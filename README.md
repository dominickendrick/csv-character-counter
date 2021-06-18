# csv-character-counter
This counts all the instances of characters per column in a csv file


With the input csv file in the format

First Header | Second Header
------------ | -------------
test | data
with | character

This code will produce the following output csv file in the format

Field name | Character | Occurence | Character Unicode name | Unicode Codepoint | UTF-8 Hex value
---------- | --------- | --------- | ---------------------- | ----------------- | ---------------
First Header | e | 1 | LATIN SMALL LETTER E | U+0065 | 65 
First Header | s | 1 | LATIN SMALL LETTER S | U+0073 | 73 
First Header | t | 3 | LATIN SMALL LETTER T | U+0074 | 74 
First Header | i | 1 | LATIN SMALL LETTER I | U+0069 | 69 
First Header | h | 1 | LATIN SMALL LETTER H | U+0068 | 68 
First Header | w | 1 | LATIN SMALL LETTER W | U+0077 | 77 
 Second Header | e | 1 | LATIN SMALL LETTER E | U+0065 | 65 
 Second Header | t | 2 | LATIN SMALL LETTER T | U+0074 | 74 
 Second Header | a | 4 | LATIN SMALL LETTER A | U+0061 | 61 
 Second Header |   | 2 | SPACE | U+0020 | 20 
 Second Header | r | 2 | LATIN SMALL LETTER R | U+0072 | 72 
 Second Header | d | 1 | LATIN SMALL LETTER D | U+0064 | 64 
 Second Header | c | 2 | LATIN SMALL LETTER C | U+0063 | 63 
 Second Header | h | 1 | LATIN SMALL LETTER H | U+0068 | 68 


## To Run

The simplist way to run is using `sbt`

The input file should be in csv format with the first row as headers.

The output file should just have the name of the file and no extension, the script will append the current timestamp and `.csv` file extension

`sbt "run -i <input file>  -o <output file>"`
