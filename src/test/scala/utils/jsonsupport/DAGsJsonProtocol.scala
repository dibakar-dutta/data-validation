package utils.jsonsupport

import spray.json._
import utils.jsonsupport.domain.DAGs

trait DAGsJsonProtocol extends DefaultJsonProtocol with DAGJsonProtocol {
  implicit val DAGsJsonFormat: RootJsonFormat[DAGs] = jsonFormat1(DAGs)
}
