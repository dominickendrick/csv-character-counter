package characterCount

object CharacterUtils {
   /**
     * Take an array of characters, and returns them as a Map with each character as the key, and the number of times the character occured as the value
    **/
    def charOccurance(charArray: Array[Char]): Map[Char, Int] = {
            charArray.toList.groupBy(identity).mapValues(_.size).toMap
    }

    def buildCharacterSetFromData(userData: List[String], headers: List[String]): Map[String, Map[Char, Int]] = {
        val userDataWithHeadersMap: Map[String, String] = headers.zip(userData).toMap
        userDataWithHeadersMap.map{ case (key, value) => key -> charOccurance(value.toCharArray())}
    }

    /** 
     * This gets the escaped string representation of a charater, the uppercase C unicode equivilant is \u0043 - https://codepoints.net/U+0043?lang=en
    **/
    def charToHex(c: Char): String = {
        val hex = Integer.toHexString(c).toUpperCase.reverse.padTo(4, '0').reverse
        s"\\u$hex"
    }

    /** 
    * This merges together two maps 
    **/
    
    def mergeColumnMaps(a: Map[String, Map[Char, Int]], b: Map[String, Map[Char, Int]] ): Map[String, Map[Char, Int]] = {
        a ++ b.map { case (column: String, characterMap: Map[Char,Int]) => {
                (column -> mergeCharacterOccuranceMap(characterMap, a.getOrElse(column, Map.empty)))
            } 
        }
    }

    /** 
    * This merges together two maps and add the integer values
    * This is to accumulate the occurace values for each character across multiple rows
    **/
    def mergeCharacterOccuranceMap (a: Map[Char, Int], b: Map[Char, Int]): scala.collection.immutable.Map[Char, Int] = {
        a ++ b.map{ case(character: Char, occurance: Int) => {
                (character -> (a.getOrElse(character, 0) + occurance))
            }
        }
    }
}
