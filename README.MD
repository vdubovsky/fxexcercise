# FX exercise

Simple Spring Boot app designated to process FX feed

- mvn clean package
- java -jar .\target\fx-exercise-0.0.1-SNAPSHOT.jar

## Init test data
curl --location --request POST 'localhost:8080/test/init'

## Simulate incoming message
curl --location 'localhost:8080/test/tick' \
--header 'Content-Type: text/plain' \
--data '3,EUR/USD,1.1123,1.300,01-08-2022 11:22:33:444
4,TSLA,1.300,1.400,01-08-2022 11:22:33:555'

## Get offer
curl --location --request GET 'localhost:8080/api/v1/offers?instrument=EUR%2FUSD' \
--header 'Content-Type: text/plain' \
--data '1,EUR/USD,1.200,1.300,01-08-2022 11:22:33:444'

## H2 console
- http://localhost:8080/h2-console
- username: sa
- password: password





