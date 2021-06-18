package com.example.spark.app

import com.example.spark.app.Models.FootballTeam
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
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

  def generate_dataset(): Unit = {
    val player = FootballTeam("artem", "league")
  }

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.WARN)
    Logger.getLogger("akka").setLevel(Level.WARN)

    generate_dataset()

    val spark = SparkSession
      .builder()
      .appName("Example SparkApplication")
      .master("local[*]")
      .getOrCreate()


    // TODO Is that ok? importing from instantiated object
    import spark.implicits._

    // An example spark application. Calculate Long.MaxValue + 100 as a big decimal.
    val df: DataFrame = spark.createDataset(Seq(FootballTeam("artem", "league"), FootballTeam("artem1", "league1"))).toDF()
    val v = spark
      .sparkContext
      .parallelize(List(Long.MaxValue, 100))
      .toDF
      .selectExpr("cast(value as decimal(38,0)) value")
      .agg(sum("value")).take(1)(0)(0)

    println(v)
  }

}
