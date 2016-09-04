package tutor

import org.scalatest.{FunSpec, ShouldMatchers}

class SourceCodeAnalyzerSpec extends FunSpec with ShouldMatchers{
  describe("SourceCode object"){
    it("can read file and create a SourceCode instance"){
      val sca = new SourceCodeAnalyzer {}
      val sourceCodeInfo = sca.processFile("./src/test/resources/sourceFileSample")
      sourceCodeInfo.name shouldBe "sourceFileSample"
      sourceCodeInfo.path shouldBe "./src/test/resources/sourceFileSample"
      sourceCodeInfo.count shouldBe 108
    }
  }
}
