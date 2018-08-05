package com.example.spark.app

import com.example.spark.testUtils.{SparkJobRunHelper, SparkLocalMaster}
import org.scalatest.FunSuite

class SampleSparkAppRunner extends FunSuite
  with SparkJobRunHelper
  with SparkLocalMaster {
      runSparkJobAsTest[SampleSparkApp.type]
  }

