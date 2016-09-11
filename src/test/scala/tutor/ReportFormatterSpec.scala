package tutor

import org.scalatest.{FunSpec, ShouldMatchers}
import tutor.utils.FileUtil

class ReportFormatterSpec extends FunSpec with ShouldMatchers{

  val rf = new ReportFormatter {}

  describe("ReportFormatter"){
    it("can format SourceCodeInfo"){
      rf.format(SourceCodeInfo("somepath","some name", 10)) shouldBe "name: some name      lines: 10"
    }
    it("can format CodebaseInfo"){
      val codebaseInfo = CodebaseInfo(Map("sbt" -> 1, "scala" -> 2, FileUtil.EmptyFileType -> 1))
      rf.format(codebaseInfo) shouldBe
        """
          |sbt     1
          |scala     2
          |empty-file-type     1
        """.trim.stripMargin
    }
  }
}
