package tutor

import org.scalatest.{FunSpec, Matchers}

class CodebaseInfoSpec extends FunSpec with Matchers{
  describe("codeBaseInfo"){
    it("empty CodeBaseInfo + SourceCodeInfo = CodeBaseInfo(contains this SourceCodeInfo)"){
      val sourceCodeInfo = SourceCodeInfo("1.scala", "1.scala", 10)
      val result = CodebaseInfo.empty + sourceCodeInfo
      result.totalFileNums shouldBe 1
      result.totalLineCount shouldBe 10
      result.top10Files shouldBe Seq(sourceCodeInfo)
      result.longestFileInfo.get shouldBe sourceCodeInfo
      result.fileTypeNums.keySet.size shouldBe 1
      result.fileTypeNums.keySet should contain("scala")
      result.fileTypeNums("scala") shouldBe 1
      result.avgLineCount shouldBe 10
    }
  }
}
