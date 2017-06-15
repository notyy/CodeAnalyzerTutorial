package tutor

import akka.actor.ActorSystem
import akka.testkit.TestProbe
import org.scalatest.{FunSpec, Matchers, ShouldMatchers}
import tutor.CodebaseAnalyzeAggregatorActor.{AnalyzeDirectory, Report}

import scala.concurrent.duration._

class CodebaseAnalyzeAggregatorActorSpec extends FunSpec with ShouldMatchers {
  describe("CodebaseAnalyzeAggregatorActor") {
    it("can analyze given file path, aggregate results of all individual files") {
      implicit val system = ActorSystem("CodebaseAnalyzeAggregator")
      val probe = TestProbe()
      val codebaseAnalyzeAggregator = system.actorOf(CodebaseAnalyzeAggregatorActor.props())
      codebaseAnalyzeAggregator.tell(AnalyzeDirectory("src/test/fixture"), probe.ref)
      val result = probe.expectMsgType[Report](3 seconds).codebaseInfo
      result.totalFileNums shouldBe 2
      result.fileTypeNums.keySet should have size 2
      result.fileTypeNums("java") shouldBe 1
      result.fileTypeNums("scala") shouldBe 1
      result.totalLineCount shouldBe 31
      result.avgLineCount shouldBe 15.0
      result.longestFileInfo.get.localPath shouldBe "SomeCode.scala"
      result.top10Files should have size 2
      result.top10Files should contain (SourceCodeInfo("/Users/twer/source/scala/CodeAnalyzerTutorial/src/test/fixture/sub/SomeCode.scala", "SomeCode.scala", 16))
    }
  }
}
