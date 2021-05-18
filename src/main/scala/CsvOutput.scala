package characterCount

import java.io._
import com.github.tototoshi.csv._
import java.lang.Character
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.Date

object CsvOutput {
      
    def writeOutput(data: Map[String,Map[Char, Int]], path: String): Unit = {    
        /** 
         * get a string representation of a date
        **/
        def getDateAsString(d: Date): String = {
            val DATE_FORMAT = "yyyy_MM_dd_HH_mm_ss"
            val dateFormat = new SimpleDateFormat(DATE_FORMAT)
            dateFormat.format(d)
        }
        
        val dateString = getDateAsString(new Date())

        val csvOutputData = formatDataAsCsvOutput(data)
        val outputFile = s"${path}${dateString}.csv"
        
        val fileHandle = new File(outputFile)
        val fileWriter = CSVWriter.open(fileHandle)
        fileWriter.writeAll(csvOutputData)

        println(s"\nWritten all data to ${outputFile}")
    }

    /** 
     * outputs data as csv with headers in the format:
     * field name, character, occurances, character UTF-8 name
    **/

    def formatDataAsCsvOutput(data: Map[String,Map[Char, Int]]): List[List[String]] = {
        data.foldLeft(List.empty[List[String]]){ 
            case (acc: List[List[String]], (key: String, value: Map[Char, Int])) => {     
                val characterEntries = value.toList.map{ 
                    case(char: Char, occurance: Int) => { 
                        val charName = Character.getName(char)
                        List(key, char.toString(), occurance.toString(), charName) 
                    }
                }
                acc ++ characterEntries
            }
        }
    }
}
