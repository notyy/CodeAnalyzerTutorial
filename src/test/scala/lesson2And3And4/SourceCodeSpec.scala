package lesson2And3And4

import org.scalatest.{FunSpec, ShouldMatchers}

class SourceCodeSpec extends FunSpec with ShouldMatchers{
  describe("SourceCode object"){
    it("can read file and create a SourceCode instance"){
      val sourceCode = SourceCode.fromFile("test/resources/sourceFileSample")
      sourceCode.name shouldBe "sourceFileSample"
      sourceCode.path shouldBe "test/resources/sourceFileSample"
      sourceCode.count shouldBe 108
    }
  }
}
