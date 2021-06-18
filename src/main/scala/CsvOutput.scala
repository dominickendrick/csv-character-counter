package characterCount

import java.io._
import com.github.tototoshi.csv._
import java.lang.Character
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.Date

object CsvOutput {

    val headers = List("Field name", "Character", "Occurence", "Character Unicode name", "Unicode Codepoint", "UTF-8 Hex value")
      
    def writeOutput(data: Map[String,Map[String, Int]], path: String): Unit = {    
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
        val csvOutputDataWithHeaders = headers :: csvOutputData
        val outputFile = s"${path}${dateString}.csv"
        
        val fileHandle = new File(outputFile)
        val fileWriter = CSVWriter.open(fileHandle)
        fileWriter.writeAll(csvOutputDataWithHeaders)

        println(s"\nWritten all data to ${outputFile}")
    }

    /** 
     * outputs data as csv with headers in the format:
     * field name, character, occurances, character Unicode name, Unicode Codepoint, Utf-8 Hex value
    **/

    def formatDataAsCsvOutput(data: Map[String,Map[String, Int]]): List[List[String]] = {
        data.foldLeft(List.empty[List[String]]){ 
            case (accumulator: List[List[String]], (header: String, value: Map[String, Int])) => {     
                val characterEntries = value.toList.map{ 
                    case(char: String, occurance: Int) => { 
                        val charName = Character.getName(char.codePointAt(0))
                        List(header, char.toString(), occurance.toString(), charName, CharacterUtils.charToUnicodeCodepoint(char), CharacterUtils.charToUTF8Hex(char)) 
                    }
                }
                accumulator ++ characterEntries
            }
        }
    }
}
