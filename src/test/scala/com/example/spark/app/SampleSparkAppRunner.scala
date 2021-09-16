package com.example.spark.app

import com.example.spark.testutils.{SparkJobRunHelper, SparkLocalMaster}
import org.scalatest.funsuite.AnyFunSuite

class SampleSparkAppRunner extends AnyFunSuite
  with SparkJobRunHelper
  with SparkLocalMaster {
      runSparkJobAsTest[SampleSparkApp.type]
  }

