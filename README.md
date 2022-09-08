## Requirements

For building and running the application you need:

- [JDK 11](https://www.oracle.com/uk/java/technologies/javase/jdk11-archive-downloads.html)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.palindrome.check.PalindromeCheckerApplication` class from your IDE.

Alternatively you can download the source code from the following Github Link


```shell
mvn spring-boot:run
```

./mvnw install
docker build --build-arg JAR_FILE=target/*.jar -t arshas/palindrome .

docker build -t arshas/palindrome .
docker volume create palindrome-db
docker run -p 8080:8080 arshas/palindrome -v palindrome-db:/tmp