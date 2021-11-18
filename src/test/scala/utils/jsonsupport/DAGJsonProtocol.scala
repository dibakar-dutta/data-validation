package utils.jsonsupport

import spray.json._
import utils.jsonsupport.domain.DAG

trait DAGJsonProtocol extends DefaultJsonProtocol {
  implicit val DAGJsonFormat = jsonFormat1(DAG)
}
