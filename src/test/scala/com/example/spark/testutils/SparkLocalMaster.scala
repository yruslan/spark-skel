package com.example.spark.testutils

trait SparkLocalMaster {
  System.getProperties.setProperty("spark.master", "local[*]")
}
