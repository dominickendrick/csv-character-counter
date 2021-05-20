# csv-character-counter
This counts all the instances of characters per column in a csv file


With the input csv file in the format

First Header | Second Header
------------ | -------------
test | data
with | character

This code will produce the following output csv file in the format

Field name | Character | Occurances | Character UTF8 Name
---------- | --------- | ---------- | -------------------
First Header|e|1|LATIN SMALL LETTER E
First Header|s|1|LATIN SMALL LETTER S
First Header|t|3|LATIN SMALL LETTER T
First Header|i|1|LATIN SMALL LETTER I
First Header|h|1|LATIN SMALL LETTER H
First Header|w|1|LATIN SMALL LETTER W
Second Header|e|1|LATIN SMALL LETTER E
Second Header|t|2|LATIN SMALL LETTER T
Second Header|a|4|LATIN SMALL LETTER A
Second Header|r|2|LATIN SMALL LETTER R
Second Header|d|1|LATIN SMALL LETTER D
Second Header|c|2|LATIN SMALL LETTER C
Second Header|h|1|LATIN SMALL LETTER H


## To Run

The simplist way to run is using `sbt`

The input file should be in csv format with the first row as headers.

The output file should just have the name of the file and no extension, the script will append the current timestamp and `.csv` file extension

`sbt "run -i <input file>  -o <output file>"`
