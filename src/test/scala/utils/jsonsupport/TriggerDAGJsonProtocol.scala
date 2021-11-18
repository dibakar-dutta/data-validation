package utils.jsonsupport

import spray.json._
import utils.jsonsupport.domain.TriggerDAG

trait TriggerDAGJsonProtocol extends DefaultJsonProtocol {
  implicit val triggerDAGFormat: RootJsonFormat[TriggerDAG] = jsonFormat1(TriggerDAG)
}

