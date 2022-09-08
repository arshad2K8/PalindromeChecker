## Requirements

For building and running the application you need:

- [JDK 11](https://www.oracle.com/uk/java/technologies/javase/jdk11-archive-downloads.html)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.palindrome.check.PalindromeCheckerApplication` class from your IDE.

- You can download the source code from the following Github Link
- [Palindrome Checker Github](https://github.com/arshad2K8/PalindromeChecker)
- pass in file.store.dir to any of your local storage path or else it defaults to /tmp
- Run following commands using a shell

```shell

cd PalindromChecker
./mvnw install

- On Mac (Please ensure file.store.dir exists )
./mvnw  spring-boot:run -Dspring-boot.run.arguments="--file.store.dir=/tmp"

- On Windows (Please ensure file.store.dir exists )
./mvnw  spring-boot:run -Dspring-boot.run.arguments="--file.store.dir=C:\tmp1"
```

## Running the application using docker

```shell
docker build --build-arg JAR_FILE=target/*.jar -t arshas/palindrome .
docker build -t arshas/palindrome .
docker volume create palindrome-db
docker run -p 8080:8080 arshas/palindrome -v palindrome-db:/tmp
```


## Verification
- [Swagger](http://localhost:8080/swagger-ui/#/palindrome-checker-controller)

Sample Request 

```shell
{
  "input": "ara"
}
```


Sample Response 
```shell
{
  "palindrome": true
}
```
