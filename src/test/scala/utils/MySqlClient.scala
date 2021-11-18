package utils

import org.apache.spark.sql.DataFrame

class MySqlClient(dataSource: DataSource) extends Session {

  val employeeDF: DataFrame = sparkSession.read
    .format("jdbc")
    .option("driver", dataSource.driver)
    .option("url", dataSource.url)
    .option("user", dataSource.user)
    .option("password", dataSource.password)
    .option("dbtable", dataSource.dbTable)
    .option("useSSL", "false")
    .load()

/**
  val datasource = sparkSession.read
    .format("jdbc")
    .option("url", "jdbc:mysql://<IP>:3306/classicmodels")
    .option("driver", "com.mysql.jdbc.Driver")
    .option("dbtable", "customers")
    .option("user", "<username>")
    .option("password", "<password>")
    .option("useSSL", "false")
    .load()
 */
}

object MySqlClient {
  def apply(): MySqlClient = new MySqlClient(MySQlDataSource)
}
