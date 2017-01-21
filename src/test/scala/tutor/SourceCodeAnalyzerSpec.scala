package tutor

import org.scalatest.{FunSpec, ShouldMatchers}

class SourceCodeAnalyzerSpec extends FunSpec with ShouldMatchers{
  describe("SourceCode object"){
    it("can read file and create a SourceCode instance"){
      val sca = new SourceCodeAnalyzer {}
      val sourceCodeInfo = sca.processFile("./src/test/fixture/sourceFileSample").get
      sourceCodeInfo.localPath shouldBe "sourceFileSample"
      sourceCodeInfo.path shouldBe "./src/test/fixture/sourceFileSample"
      sourceCodeInfo.count shouldBe 108
    }
  }
}
