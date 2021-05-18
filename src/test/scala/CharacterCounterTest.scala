package characterCount

import org.scalatest.freespec.AnyFreeSpec
import scala.collection.mutable.HashMap
import org.scalatest._
import matchers.should._



class CharacterCounterTest  extends AnyFreeSpec with Matchers {

  "building objects" - {

        "should build char array" in {
            val charArray = Array('A','A','b','c','d')
            CharacterCounter.charOccurance(charArray) should equal(Map('A' -> 2, 'b' -> 1, 'c' -> 1, 'd' -> 1))
        }

        "should build Map from row" in {
            val data = List("1234asdfvesasdf","45fdf54b-674d-4561-84cf-c1e5da61f4fa")
            CharacterCounter.buildCharacterSetFromData(data, List("one", "two")) should equal(Map("one" -> HashMap('e' -> 1, 's' -> 3, '4' -> 1, 'f' -> 2, 'a' -> 2, 'v' -> 1, '1' -> 1, '2' -> 1, '3' -> 1, 'd' -> 2), "two" -> HashMap('e' -> 1, '8' -> 1, '4' -> 6, 'f' -> 5, 'a' -> 2, '5' -> 4, '-' -> 4, 'b' -> 1, 'c' -> 2, '7' -> 1, 'd' -> 3, '6' -> 3, '1' -> 3)))

            
        }

        "should create csv output" in {
            val data: Map[String, Map[Char, Int]] = Map("test" -> Map('a' -> 1, 'b' -> 2), "anotherrow" -> Map('a' -> 1, 'b' -> 2))
            CharacterCounter.csvOutput(data) should equal(List(List("test", "a", "1", "LATIN SMALL LETTER A"), List("test", "b", "2", "LATIN SMALL LETTER B"), List("anotherrow", "a", "1", "LATIN SMALL LETTER A"), List("anotherrow", "b", "2", "LATIN SMALL LETTER B")))


        }
        
    } 


  
}