# Spark S3 example application

The purpose of this project is to provide
- An example of a Spark application that can access S3
- Best practices for authenticating to AWS from Spark
- The documentation on how Spark can be ran in local mode without `spark-submit`
- An uber jar that can be used to run other Spark applications locally that use S3
- Necessary classes to use `magic` committer with Parquet.

## Usage


### To run the example application

To run the application, 
- If you want to explicitly specify AWS credentials, create `application.conf` and add it there.
  Use `reference.conf` from the resources as an example.
- Run the Spark application locally:

```sh
java -cp spark-s3-0.0.1-SNAPSHOT.jar \
  com.example.spark.s3.app.SparkS3App
```

### To run a custom SPark application in local mode with S3 support
When running custom Spark application using the uber jar generated from this example keep in mind that
`log4j.properties` need to be either explicitly provided or be included in the jar file of the custom Spark app.
Also, the version of Spark and Scala of the uber jar should be the same as of the custom Spark app.

- Copy `log4j.properties` from the resources folder to the current directory
- If you want to explicitly specify AWS credentials, create `application.conf` and add it there.
  Use `reference.conf` from the resources as an example.
- Run the Spark application locally:

```sh
java -Dlog4j.configuration=file:"log4j.properties" \
  -cp spark-s3-0.0.1-SNAPSHOT.jar:myspark-app.jar \
  com.example.spark.s3.app.SparkS3App
```

## Build

**To run this locally use an IDE**

**To build an uber jar**
```
mvn clean package
```

## Building an uber jar to enable running in the local mode with S3 support
