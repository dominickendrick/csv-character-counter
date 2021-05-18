package characterCount

import java.io._
import java.io.File
import com.github.tototoshi.csv._
import pb._
import java.lang.Character
import scala.io.Source
import java.io.{BufferedInputStream, FileInputStream}
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.Date

import scala.util.{Success, Failure}
import scala.concurrent._
import akka.stream.alpakka.csv.scaladsl.CsvParsing
import akka.stream.scaladsl._
import akka.actor.ActorSystem
import akka.{ NotUsed, Done }
import akka.actor.ActorSystem
import akka.util.ByteString
import scala.concurrent._
import scala.concurrent.duration._
import java.nio.file.Paths
import java.util.concurrent.{ArrayBlockingQueue, TimeUnit}
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy

import akka.stream._
import akka.stream.scaladsl._
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.ThreadPoolExecutor


case class Config(
    input: String = "",
    output: String = ""
)

object CharacterCounter extends App {

    private val blockingQueue = new ArrayBlockingQueue[Runnable](64)
    private val finiteThreadPool = new ThreadPoolExecutor(36, 36, 60, TimeUnit.SECONDS, blockingQueue, new AbortPolicy())
    private implicit val ec = ExecutionContext.fromExecutor(finiteThreadPool)

    implicit val actorSystem = ActorSystem("QuickStart")
    implicit val materializer = ActorMaterializer()

    import scopt.OParser
    val builder = OParser.builder[Config]
    val parser1 = {
    import builder._
    /**
      * This parses the command line options and builds a Config case class
    */

    OParser.sequence(
        programName("csv-character-counter"),
        head("csv-character-counter", "1.x"),
        opt[String]('i', "input")
        .required()
        .valueName("<file>")
        .action((x, c) => c.copy(input = x))
        .text("input is a required file property"),
        opt[String]('o', "output")
        .required()
        .valueName("<file>")
        .action((x, c) => c.copy(output = x))
        .text("out is a required file property"))
    }

    /**
      * If the command line argument parser run successfully, run the stream
      */
    OParser.parse(parser1, args, Config()) match {
        case Some(config) => runStream(config)
        case _ => // arguments are bad, error message will have been displayed
    }

    def runStream(config: Config) = {

        //get the number of lines in the input file, excluding headers
        val numberOfLinesInCsvFile = Source.fromFile(config.input).getLines.size 
        val numberOfLinesInCsvFileExcludingHeaders = numberOfLinesInCsvFile - 1

        // set up the progress bar which will display on the screen for the user
        var progressBar = new ProgressBar(numberOfLinesInCsvFileExcludingHeaders)
        progressBar.showSpeed = false

        // Get the headers from the csv file as a List[String]
        val csvDataReader: CSVReader = CSVReader.open(new File(config.input))
        val headers = csvDataReader.readNext().getOrElse(List.empty)
        
        // This builds the final output data by merging together the results of parsing each line of the csv file
        val mergeColumnMapsSink: Sink[Map[String,Map[Char,Int]], Future[Map[String,Map[Char,Int]]]] = {
            Sink.fold[Map[String,Map[Char,Int]], Map[String,Map[Char,Int]]](Map.empty[String,Map[Char,Int]]){(acc, element) => CharacterUtils.mergeColumnMaps(acc, element)}
        }

        val csvFileCharacterCountingStream = FileIO.fromPath(Paths.get(config.input))
        .via(CsvParsing.lineScanner()) //Scan line by line as CSV format with the output of List[String] per line
        .map(_.map(_.utf8String)) //convert each line of the csv into a utf8 string
        .drop(1) // drop the header row from the stream
        .map{data => progressBar += 1; data} //update the progress bar
        .map(CharacterUtils.buildCharacterSetFromData(_, headers)) 
        .toMat(mergeColumnMapsSink)(Keep.right)

        //When we have interated over each line of the input file, we write the output to disk
        csvFileCharacterCountingStream.run().onComplete{data => 
            data match {
                case Success(data) => CsvOutput.writeOutput(data, config.output)
                case _ => println("failed")
            }
            actorSystem.terminate()
        }
        Await.result(actorSystem.whenTerminated, Duration.Inf)
    }

}