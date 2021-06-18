package characterCount

import java.nio.charset.StandardCharsets

object CharacterUtils {
   /**
     * Take an array of characters, and returns them as a Map with each character as the key, and the number of times the character occured as the value
    **/
    def charOccurance(charArray: Array[String]): Map[String, Int] = {
            charArray.toList.groupBy(identity).mapValues(_.size).toMap
    }

    def buildCharacterSetFromData(userData: List[String], headers: List[String]): Map[String, Map[String, Int]] = {
        val userDataWithHeadersMap: Map[String, String] = headers.zip(userData).toMap
        userDataWithHeadersMap.map{ case (key, value) => key -> charOccurance(value.split(""))}
    }

    def utf8Encode(codepoint: Int): Array[Byte] = {
        new String(Array[Int](codepoint), 0, 1).getBytes(StandardCharsets.UTF_8)
    }

    /**
     * This gets the UTF-8 Hex representation of the character
    **/
    def charToUTF8Hex(char: String): String = {
        val codePoint = char.codePointAt(0)
        val bytes = utf8Encode(codePoint)
        bytes.foldLeft("")(_ + "%02X ".format(_))
    } 

    /** 
     * This gets the Unicode codepoint string of a charater, the uppercase C unicode equivilant is \u0043 - https://codepoints.net/U+0043?lang=en
    **/
    def charToUnicodeCodepoint(char: String): String = {
        val codePoint = char.codePointAt(0)
        f"U+${codePoint}%04X"
    }

    /** 
    * This merges together two maps of headers and their character count maps
    **/
    
    def mergeColumnMaps(a: Map[String, Map[String, Int]], b: Map[String, Map[String, Int]] ): Map[String, Map[String, Int]] = {
        a ++ b.map { case (column: String, characterMap: Map[String,Int]) => {
                (column -> mergeCharacterOccuranceMap(characterMap, a.getOrElse(column, Map.empty)))
            } 
        }
    }

    /** 
    * This merges together two maps and add the integer values
    * This is to accumulate the occurace values for each character across multiple rows
    **/
    def mergeCharacterOccuranceMap (a: Map[String, Int], b: Map[String, Int]): scala.collection.immutable.Map[String, Int] = {
        a ++ b.map{ case(character: String, occurance: Int) => {
                (character -> (a.getOrElse(character, 0) + occurance))
            }
        }
    }
}
