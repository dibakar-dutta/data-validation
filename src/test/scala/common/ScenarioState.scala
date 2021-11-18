package common

import io.cucumber.scala.{ScalaDsl, Scenario}
import org.apache.spark.sql.DataFrame

trait ScenarioState extends ScalaDsl {

  private var _mySqlEmployeesDF: Option[DataFrame] = None
  private var _postgresEmployeesDF: Option[DataFrame] = None

  private var _dagRunId: Option[String] = None

  def mySqlEmployeesDF: DataFrame = _mySqlEmployeesDF.getOrElse(throw new IllegalStateException("Not initialised"))
  def postgresEmployeesDF: DataFrame = _postgresEmployeesDF.getOrElse(throw new IllegalStateException("Not initialised"))

  def store(mysqlDF: DataFrame, postgresDF: DataFrame): Unit = {
    _mySqlEmployeesDF = Some(mysqlDF)
    _postgresEmployeesDF = Some(postgresDF)
  }

  def storeDAGRunId(dagRunId: String): Unit = _dagRunId = Some(dagRunId)

  def dagRunId: String = _dagRunId.getOrElse(throw new IllegalStateException("Not initialised"))

  Before { scenario: Scenario =>
    reset()
  }

  private def reset(): Unit = {
    _mySqlEmployeesDF = None
    _postgresEmployeesDF = None
  }

}
