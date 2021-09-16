package com.example.spark.testutils

import org.scalatest.funsuite.AnyFunSuiteLike

import scala.reflect.ClassTag
import scala.reflect.runtime.universe

trait SparkJobRunHelper {
  this: AnyFunSuiteLike =>

  private def runSparkJob[T](implicit ct: ClassTag[T]): Unit = {
    type MainClass = {def main(args: Array[String]): Unit}

    val jobClass = ct.runtimeClass
    val jobClassSymbol = universe runtimeMirror jobClass.getClassLoader classSymbol jobClass

    val jobInstance =
      if (jobClassSymbol.isModuleClass)
        jobClass.getField("MODULE$").get(jobClass)
      else
        jobClass.newInstance()

    jobInstance.asInstanceOf[MainClass].main(Array.empty)
  }

  def runSparkJobAsTest[T](implicit ct: ClassTag[T]): Unit = {
    val simpleName = ct.runtimeClass.getSimpleName
    test(simpleName)(runSparkJob[T](ct))
  }

}
