package steps

import com.amazon.deequ.VerificationResult
import com.amazon.deequ.VerificationSuite
import com.amazon.deequ.checks.{Check, CheckLevel, CheckStatus}
import common.ScenarioState
import io.cucumber.scala.{EN, ScalaDsl}
import org.apache.spark.sql.functions._
import org.scalatest.matchers.should._
import utils.jsonsupport.domain.TriggerDAGResponse
import utils.{AirflowClient, MySqlClient, PostgresClient}

import scala.concurrent.Await
import scala.concurrent.duration._

import scala.concurrent.Future

class StepDefinitions
  extends ScalaDsl
    with EN
    with Matchers
    with ScenarioState {

  Given("""^Airflow DAG job (.*) is triggered successfully""") { (dagId: String) =>
    val triggerDAGResponseFuture: Future[TriggerDAGResponse] = AirflowClient().triggerDAG(dagId)
    triggerDAGResponseFuture.foreach(triggerDAGResponse => storeDAGRunId(triggerDAGResponse.dag_run_id))(AirflowClient().executionContext)
  }

  When("""^Airflow DAG job with (.*) is executed successfully""") { (dagId: String) =>
    val result = AirflowClient().executeDAGRunStatusUntilSuccess(dagId, dagRunId)
    Await.result(result, Duration.Inf)
  }

  Then("""Airflow DAG job status is marked as “Complete”""") { () =>
    succeed
  }

  And("""^Data from (.*) to (.*) loaded successfully""") { (sourceTable: String, targetTable:String) =>
    val mySqlDF = MySqlClient().readTable(sourceTable)
    val postgresDF = PostgresClient().readTable(targetTable)

    store(mySqlDF, postgresDF)
  }

  Then("""Target table is loaded with transformed data and it is matching between each other""") { () =>
    val mySqlTransformedDF = mySqlEmployeesDF.select(
      expr("id"),
      concat(col("first_name"), lit(" "), col("last_name")).as("name"),
      concat(col("email"), lit("@emids.com")).as("email")
    ).withColumn("role", expr("if(name = 'Dibakar Dutta', 'engineer', 'architect')"))

    //mySqlTransformedDF.withColumn("role", expr("if(name = 'Dibakar Dutta', 'engineer', 'architect')")).show

    val mySqlTransformedData = mySqlTransformedDF.take(10)
    val postgresData = postgresEmployeesDF.take(10)

    println("==================== source data ==============")
    mySqlEmployeesDF.show
    println("=================== source data end ===========")

    println("=========target data start ===============")
    postgresEmployeesDF.show()
    println("=====target data end====")

    println("=========Direct Comparison Source and Target ===============")
    mySqlTransformedDF.except(postgresEmployeesDF).show()
    println("=====Direct Comparison Source and Target end====")

    postgresData should contain theSameElementsAs mySqlTransformedData
  }

  Then("""Target table data quality should be as expected""") { () =>

    val verificationResult: VerificationResult = VerificationSuite()
      .onData(postgresEmployeesDF)
      .addCheck(
        Check(CheckLevel.Error, "unit testing my data")
          .hasSize(_ == 2) // we expect 2 rows
          .isComplete("id") // should never be NULL
          .isUnique("id") // should not contain duplicates
          .isComplete("name") // should never be NULL
          .isComplete("email") // should never be NULL
          .containsEmail("email", _ == 1.0) //% of row match
          .isComplete("role") // should never be NULL
          .isContainedIn("role", Array("engineer", "architect")) // should only contain the values "engineer" and "architect"
          .containsSocialSecurityNumber("SSN")
      )
      .run()

    println("==========================")
    println("==========================")
    //println(verificationResult.status)
    println(verificationResult.checkResults)
    println("==========================")
    println("==========================")

    verificationResult.status should be (CheckStatus.Success)
  }
  Then("""^Target table data quality should be Unique for the Column (.*)""") { (ColumnName:String) =>

    val verificationResult: VerificationResult = VerificationSuite()
      .onData(postgresEmployeesDF)
      .addCheck(
        Check(CheckLevel.Error, "unit testing my data")
          .isUnique(ColumnName) // should not contain duplicates
      )
      .run()

    println("==========================")
    println("==========================")
    //println(verificationResult.status)
    println(verificationResult.checkResults)
    println("==========================")
    println("==========================")

    verificationResult.status should be (CheckStatus.Success)
  }

  Then("""^Target table data quality should be Complete for the Column (.*)""") { (ColumnName:String) =>

    val verificationResult: VerificationResult = VerificationSuite()
      .onData(postgresEmployeesDF)
      .addCheck(
        Check(CheckLevel.Error, "unit testing my data")
          .isComplete(ColumnName) // should not contain duplicates
      )
      .run()

    println("==========================")
    println("==========================")
    //println(verificationResult.status)
    println(verificationResult.checkResults)
    println("==========================")
    println("==========================")

    verificationResult.status should be (CheckStatus.Success)
  }
}


