package com.example.spark.app

import com.typesafe.config.ConfigFactory
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._


/**
  * This is an example Spark Application.
  *
  * To run this locally use `mvn test`
  * or change Scala and Spark dependencies from `provided` to `compile`
  *
  * To run this on cluster generate the uber jar by running
  * `mvn package` and use `spark-submit` on cluster.
  */
object SampleSparkApp {

  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load()
    val logger = Logger.getLogger(this.getClass)

    val applicationVersion = config.getString("application.version")
    val buildTimestamp = config.getString("build.timestamp")

    Logger.getLogger("org").setLevel(Level.WARN)
    Logger.getLogger("akka").setLevel(Level.WARN)

    val spark = SparkSession
      .builder()
      .appName("Example SparkApplication")
      .master("local[*]")
      .config("spark.ui.enabled", "false")
      .config("spark.driver.bindAddress","127.0.0.1")
      .config("spark.driver.host", "127.0.0.1")
      .config("spark.sql.session.timeZone", "Etc/UTC")
      .getOrCreate()

    import spark.implicits._

    logger.info(s"Application version: $applicationVersion (built $buildTimestamp)")

    // An example spark application. Calculate Long.MaxValue + 100 as a big decimal.
    val v = spark
      .sparkContext
      .parallelize(List(Long.MaxValue, 100))
      .toDF
      .selectExpr("cast(value as decimal(38,0)) value")
      .agg(sum("value")).take(1)(0)(0)

    println(v)
  }

}
