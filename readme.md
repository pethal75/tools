# Application for monitoring and controlling servers infrastructure

## Compilation

Compile application using maven:

```mvn clean compile```

## Running

Run application with maven command:

```mvn spring-boot:run```

Or build jar archive:

```mvn compile package spring-boot:repackage```

and run final WAR archive

```java -jar target\servers-1.0-SNAPSHOT.war```

## Properties

To run application, tools.properties file should be present in working directory:

```
tools.last-project=project_name
tools.storage.directory=./directory
tools.storage.type=FILE
```
