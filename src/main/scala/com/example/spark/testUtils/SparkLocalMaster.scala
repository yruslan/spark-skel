package com.example.spark.testUtils

trait SparkLocalMaster {
  System.getProperties.setProperty("spark.master", "local[*]")
}
