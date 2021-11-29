package utils

import org.apache.spark.sql.DataFrame

class PostgresClient(dataSource: DataSource) extends Session {
//  val employeeDF: DataFrame = sparkSession.read
//    .format("jdbc")
//    .option("driver", dataSource.driver)
//    .option("url", dataSource.url)
//    .option("user", dataSource.user)
//    .option("password", dataSource.password)
//    .option("dbtable", dataSource.dbTable)
//    .load()

  def readTable(table: String): DataFrame =
    sparkSession.read
      .format("jdbc")
      .option("driver", dataSource.driver)
      .option("url", dataSource.url)
      .option("user", dataSource.user)
      .option("password", dataSource.password)
      .option("dbtable", table)
      .load()
}


object PostgresClient {
  def apply(): PostgresClient = new PostgresClient(PostgresDataSource)
}
