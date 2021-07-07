Link Converter

## Requirements

For building and running the application you need:

- [JDK 16](https://www.oracle.com/java/technologies/javase-jdk16-downloads.html)
- [Maven](https://maven.apache.org)
- [Mysql](https://www.mysql.com/downloads/)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com/trendyoltech/linkconverter/LinkConverterApplication.java` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

or

from your terminal in root directory simply run :
```shell
mvn clean install
java -jar target/link-converter-0.0.1-SNAPSHOT.jar
```

swagger url:

you can check all endpoints from this address
```http://localhost:8080/swagger-ui.html#/ ```


## Operations :

--Web-Url-To-Deep-Link
```shell
curl -X POST "http://localhost:8080/converter/web-url-to-deep-link" -H "accept: */*" -H "Content-Type: application/json" -d 
"{
  "url": "https://www.trendyol.com/casio/saat-p-1925865?boutiqueId=439892&merchantId=105064",
}"
```
--Deep-Link-To-Web-Url

```shell
curl -X POST "http://localhost:8080/converter/deep-link-to-web-url" -H "accept: */*" -H "Content-Type: application/json" -d 
"{
  "url": "ty://?Page=Product&ContentId=1925865&CampaignId=439892",
}"
```

*An exception is thrown if the web url does not start with "https://www.trendyol.com/"

*Exception is thrown if URL contains spaces

*We have openapi support, you can see swagger documentation

Notes to reviewers;

*I wanted to use couchbase but m1 macbook doesn't support couchbase image

*I couldn't create enough time for testing and Dockerize operations. I mention it so that you don't waste time searching for the project.

*It's sad, but I did the request and response store process on the service layer. I tried to write a filter but failed. Başaramadım :)


