# Spark Job skeleton project

This is an example Apache Spark Job that can be used for creating other Spark projects. It includes all dependencies to run the job locally and on cluster.

The project generates source and javadoc jars so it can be used for creating Spark library projects as well.

## Usage 

The example application is in `com.example.spark.SampleSparkApp` object.

**To run this locally use**
```sh
mvn test
```
or change Scala and Spark dependencies from `provided` to `compile`.

**To run this on cluster generate the uber jar by running**
```
mvn package
```
and use `spark-submit` on cluster.

### Troubleshooting
If you try to run the example from an IDE you'll likely get the following exception: 

```Exception in thread "main" java.lang.NoClassDefFoundError: scala/collection/Seq```

This is because the jar is created with all Scala and Spark dependencies removed (using shade plugin). This is done so that the uber jar for `spark-submit` is not too big.

To run the job from an IDE use `SampleSparkAppRunner` test. When running tests all provided dependencies will be loaded.
