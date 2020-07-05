# Currency Converter

##### Simple Java program for currency conversion.

## About the app

One of job tasks I got while looking for my first software developer position.\
Application made using <i><b>Java 11, OpenCSV, Maven Wrapper and Maven itself</b></i>.
 
Conversion rates loaded from CSV file before any currency conversion calculations made; App uses OpenCSV library to parse CSV file.

Made a decent effort to write clean OOP code to my Date.now() best understanding, like separation of concerns and encapsulation of internal workings of the class to hide details from outside while providing a simple interface to work with a class and there should be no to little pain adding new functionality.

## How to set up

Open terminal and use git clone command to download the remote GitHub repository to your computer:
```
git clone https://github.com/skarware/job_task_currency_converter.git
```
It will create a new folder with same name as GitHub repository "job_task_currency_converter". All the project files and git data will be cloned into it. After cloning complete change directories into that new folder:
```
cd job_task_currency_converter
```
To compile the application into executable JAR run this command (uses maven wrapper):
```
./mvnw clean package
```
Or using your installed maven version:
```
mvn clean package
```
## How to run
To run the program use java JRE with executable JAR file and three arguments: amountToConvert fromCurrency toCurrency
(it is important that jar executed from main app dir or FileNotFound Exception will be thrown at runtime) 
```
java -jar target/currency_converter-1.0-SNAPSHOT-jar-with-dependencies.jar 3 EUR USD
```
If all went well you should see following lines printed to stdout:
```
skarware@citadel:~job_task_currency_converter$ java -jar target/currency_converter-1.0-SNAPSHOT-jar-with-dependencies.jar 3 EUR USD
Loading exchange rates from a FILE...   data successfully loaded.
3.00 EUR => 3.705750000554009625 USD
```