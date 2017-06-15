package tutor

import akka.actor.ActorSystem
import akka.testkit.TestProbe
import org.scalatest.FunSpec
import tutor.CodebaseAnalyzeAggregatorActor.Complete
import tutor.SourceCodeAnalyzerActor.NewFile

import scala.util.Success

class SourceCodeAnalyzerActorSpec extends FunSpec {
  describe("SourceCodeAnalyzerActor"){
    it("can analyze given file path, and reply with SourceCodeInfo"){
      implicit val system = ActorSystem("SourceCodeAnalyzer")
      val probe = TestProbe()
      val sourceCodeAnalyzerActor = system.actorOf(SourceCodeAnalyzerActor.props())
      sourceCodeAnalyzerActor.tell(NewFile("src/test/fixture/sub/SomeCode.scala"), probe.ref)
      probe.expectMsg(Complete(Success(SourceCodeInfo(path = "src/test/fixture/sub/SomeCode.scala",
        localPath = "SomeCode.scala", 16))))
    }
  }
}
