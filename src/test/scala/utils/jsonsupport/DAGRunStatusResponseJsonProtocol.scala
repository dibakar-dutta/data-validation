package utils.jsonsupport

import spray.json._
import utils.jsonsupport.domain.DAGRunStatusResponse

trait DAGRunStatusResponseJsonProtocol extends DefaultJsonProtocol{
  implicit val dAGRunStatusResponseJsonFormat: RootJsonFormat[DAGRunStatusResponse] = jsonFormat3(DAGRunStatusResponse)
}
