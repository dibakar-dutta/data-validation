package utils

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.BasicHttpCredentials
import akka.stream.ActorMaterializer
import akka.testkit.TestKit
import com.typesafe.config.ConfigFactory
import spray.json._
import utils.jsonsupport.JsonSupport
import utils.jsonsupport.domain.{DAGRunStatusResponse, DAGs, TriggerDAG, TriggerDAGResponse}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.util.Random


class AirflowClient extends TestKit(ActorSystem("airflow_client")) with JsonSupport {

//  val configString = "akka.http.server.parsing.illegal-header-warnings = off"
//
//  val config = ConfigFactory.parseString(configString)
//
//  implicit val system = ActorSystem("airflow_client"/*, ConfigFactory.load(config)*/)


  implicit val materializer = ActorMaterializer()

//  import system.dispatcher
//
//  val dispatch = dispatcher

  // for use with Futures, Scheduler, etc.
  implicit val executionContext = system.dispatchers.lookup("my-dispatcher")

  private val dagId = "compute_training_model_accuracy"

  private val baseUri = "http://localhost:8080/api/v1/dags"

  def getDAGsUri: String = baseUri
  def triggerDAGUri(dagId: String): String = s"$baseUri/$dagId/dagRuns"
  def getDAGRunStatusUri(dagId: String, dagRunId: String): String = s"$baseUri/$dagId/dagRuns/$dagRunId"


  def getDAGs(): Future[DAGs] = {
    val request: HttpRequest = getRequest(uri = getDAGsUri)
    val response = sendRequest(request)
    response.map(_.parseJson.convertTo[DAGs])
  }

  def triggerDAG(dagId: String = dagId): Future[TriggerDAGResponse] = {
    val dagRunId = "dag_run_id_" + Random.nextInt(10000)
    val requestBody = TriggerDAG(dagRunId)
    val json = requestBody.toJson.prettyPrint

    val request: HttpRequest = getRequest(
      uri = triggerDAGUri(dagId),
      HttpMethods.POST,
      Some(json)
    )

    val response = sendRequest(request)

    println("========= trigger DAG run response =========")
    Thread.sleep(100)
    println(Await.result(response, 5.seconds))
    println("========= response end =========")

    response.map(_.parseJson.convertTo[TriggerDAGResponse])
  }

  def executeDAGRunStatusUntilSuccess(dagId: String = dagId, dagRunId: String): Future[Boolean] = {
    getDAGRunStatus(dagId, dagRunId).flatMap {
      case DAGRunStatusResponse(_, _, status @ ("running" | "queued")) =>
        println(s"DAG status: $status... retrying in 1 second \n")
        Thread.sleep(1000)
        executeDAGRunStatusUntilSuccess(dagId, dagRunId)
      case DAGRunStatusResponse(_, _, "success") =>
        println("DAG tasks successfully completed")
        Future.successful(true)
    }
  }

  private def getDAGRunStatus(dagId: String, dagRunId: String): Future[DAGRunStatusResponse] = {
    val request: HttpRequest = getRequest(uri = getDAGRunStatusUri(dagId, dagRunId))
    val response = sendRequest(request)
    response.map(_.parseJson.convertTo[DAGRunStatusResponse])
  }

  private def getRequest(uri: Uri, method: HttpMethod = HttpMethods.GET, requestBody: Option[String] = None): HttpRequest = {
    val authorization = headers.Authorization(BasicHttpCredentials("airflow", "airflow"))
    val emptyRequest = HttpRequest(
      method = method,
      uri = uri,
      headers = List(authorization)
    )
    requestBody
      .map(emptyRequest.withEntity(ContentTypes.`application/json`, _))
      .getOrElse(emptyRequest)
  }

  private def sendRequest(request: HttpRequest): Future[String] = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(request)
    val entityFuture: Future[HttpEntity.Strict] = responseFuture.flatMap(response => response.entity.toStrict(5.seconds))
    entityFuture.map(entity => entity.data.utf8String)
  }
}

object AirflowClient {
  def apply() = new AirflowClient
}
