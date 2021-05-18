# csv-character-counter
This counts all the instances of characters per column in a csv file

## To Run

The simplist way to run is using `sbt`

The input file should be in csv format with the first row as headers
The output file should just have the name of the file and no extension, the script will append the current timestamp and `.csv` file extension

`sbt "run -i <input file>  -o <output file>`
