/*
 * Copyright 2021 ABSA Group Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.spark.s3.app

import com.example.spark.s3.app.utils.{ConfigUtils, SparkS3Utils}
import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.{SaveMode, SparkSession}

object SparkS3App {
  def main(args: Array[String]): Unit = {
    implicit val spark: SparkSession = buildLocalSparkSession()

    runMySparkApp()
  }

  /**
    * This is a VPN-friendly generic way to create a Spark session in local master mode.
    * (The Spark master is defined in the configuration, see `reference.conf`)
    */
  def buildLocalSparkSession(): SparkSession = {
    val conf = ConfigFactory.load()

    val sparkSessionBuilder = SparkSession
      .builder()
      .appName("Spark S3 example app")
      .config("spark.ui.enabled", "false")
      .config("spark.driver.bindAddress", "127.0.0.1")
      .config("spark.driver.host", "127.0.0.1")

    // Apply extra Spark configuration from 'reference.conf' and 'application.conf'
    val extraOptions = ConfigUtils.getExtraOptions(conf, "spark.conf.option")
    ConfigUtils.logExtraOptions("Extra Spark Config:", extraOptions)
    val sparkSessionBuilderWithExtraOptApplied = extraOptions.foldLeft(sparkSessionBuilder) {
      case (builder, (key, value)) => builder.config(key, value)
    }

    // Create a Spark session
    val spark = sparkSessionBuilderWithExtraOptApplied.getOrCreate()

    // Apply Hadoop configuration to enable S3 access
    SparkS3Utils.enableSparkS3FromConfig(spark, conf)

    spark
  }

  /**
    * This is the business logic of the Spark application. Replace the below queries with yours.
    */
  def runMySparkApp()(implicit spark: SparkSession): Unit = {
    import spark.implicits._

    // Example data frame
    val df = List(("A", 1), ("B", 2), ("C", 3)).toDF("a", "b")

    // Reading from S3
    val df1 = spark
      .read
      .option("header", "true")
      .csv("s3a://mybucket/example1")

    df1.printSchema()

    df1.show

    // Test CSV
    df.write
      .mode(SaveMode.Overwrite)
      .option("header", "true")
      .csv("s3a://mybucket/example2")

    // Test Parquet
    df.write
      .mode(SaveMode.Overwrite)
      .parquet("s3a://mybucket/example3")
  }
}
