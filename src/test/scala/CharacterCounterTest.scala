package characterCount

import org.scalatest.freespec.AnyFreeSpec
import scala.collection.mutable.HashMap
import org.scalatest._
import matchers.should._



class CharacterCounterTest  extends AnyFreeSpec with Matchers {

  "building objects" - {

        "should build char array" in {
            val charArray = Array("A","A","b","c","d")
            CharacterUtils.charOccurance(charArray) should equal(Map("A" -> 2, "b" -> 1, "c" -> 1, "d" -> 1))
        }

        "should build Map from row" in {
            val data = List("1234asdfvesasdf","45fdf54b-674d-4561-84cf-c1e5da61f4fa")
            CharacterUtils.buildCharacterSetFromData(data, List("one", "two")) should equal(Map("one" -> HashMap("e" -> 1, "s" -> 3, "4" -> 1, "f" -> 2, "a" -> 2, "v" -> 1, "1" -> 1, "2" -> 1, "3" -> 1, "d" -> 2), "two" -> HashMap("e" -> 1, "8" -> 1, "4" -> 6, "f" -> 5, "a" -> 2, "5" -> 4, "-" -> 4, "b" -> 1, "c" -> 2, "7" -> 1, "d" -> 3, "6" -> 3, "1" -> 3)))
        }

        "should merge two character occurance maps together" in {
            val map1 = Map("a" -> 1, "c" -> 1)
            val map2 = Map("a" -> 3, "b" -> 5, "c" -> 3)
            CharacterUtils.mergeCharacterOccuranceMap(map1, map2) should equal(Map("a" -> 4, "c" -> 4, "b" -> 5))
        }

        "should merge two field character occurance maps together" in {
            val map1 = Map("Header1" -> Map("a" -> 1, "c" -> 1), "Header2" -> Map("a" -> 1))
            val map2 = Map("Header1" -> Map("a" -> 3, "b" -> 5, "c" -> 3), "Header2" -> Map("a" -> 1))
            CharacterUtils.mergeColumnMaps(map1, map2) should equal(Map("Header1" -> Map("a" -> 4, "c" -> 4, "b" -> 5), "Header2" -> Map("a" -> 2)))
        }

        

        "should create csv output" in {
            val data: Map[String, Map[String, Int]] = Map("test" -> Map("a" -> 1, "b" -> 2), "anotherrow" -> Map("a" -> 1, "b" -> 2))
            CsvOutput.formatDataAsCsvOutput(data) should equal(List(List("test", "a", "1", "LATIN SMALL LETTER A", "U+0061", "61 "), List("test", "b", "2", "LATIN SMALL LETTER B", "U+0062", "62 "), List("anotherrow", "a", "1", "LATIN SMALL LETTER A", "U+0061", "61 "), List("anotherrow", "b", "2", "LATIN SMALL LETTER B", "U+0062", "62 ")))
        }

        "should correctly encode 4-byte UTF8 characters " in {
            CharacterUtils.charToUnicodeCodepoint("🦚")should equal("U+1F99A")
        }

        "should correctly build UTF8 Hex value " in {
            CharacterUtils.charToUTF8Hex("🦚")should equal("F0 9F A6 9A ")
        }
    } 
}