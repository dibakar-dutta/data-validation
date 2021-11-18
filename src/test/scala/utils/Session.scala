package utils

import org.apache.spark.sql.SparkSession

trait Session {

  final val sparkSession: SparkSession = SparkSession.builder()
    .appName("Data Validator")
    .config("spark.master", "local")
    .getOrCreate()
}
