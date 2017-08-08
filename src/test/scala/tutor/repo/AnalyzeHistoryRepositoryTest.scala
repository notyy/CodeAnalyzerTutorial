package tutor.repo

import scala.concurrent.ExecutionContext.Implicits.global
import org.scalatest.{BeforeAndAfter, FunSpec, Matchers}
import tutor.CodebaseInfo

import scala.concurrent.Await
import scala.concurrent.duration._

class AnalyzeHistoryRepositoryTest extends FunSpec with Matchers with Schemas with H2DB
  with AnalyzeHistoryRepository with BeforeAndAfter {

  before {
    Await.result(setupDB(), 5 seconds)
  }

  after {
    Await.result(dropDB(), 5 seconds)
  }

  describe("AnalyzeHistoryRecorder"){
//    it("should create tables in h2"){
//      AnalyzeHistoryRecorder.setupDB()
//    }
    it("can insert analyzeHistory"){
      val c = Await.result(
        record("some path",CodebaseInfo(1, Map("java" -> 1), 1, 10,None,Nil))
        , 10 seconds)
      c shouldBe 1
    }
  }
}
