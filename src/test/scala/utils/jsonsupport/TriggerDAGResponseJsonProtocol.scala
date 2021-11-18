package utils.jsonsupport

import spray.json._
import utils.jsonsupport.domain.TriggerDAGResponse

trait TriggerDAGResponseJsonProtocol extends DefaultJsonProtocol {
  implicit val triggerDAGResponseJsonFormat: RootJsonFormat[TriggerDAGResponse] = jsonFormat3(TriggerDAGResponse)
}
