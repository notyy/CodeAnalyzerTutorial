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
      val codebaseInfo = CodebaseInfo(Map("sbt" -> 1, "scala" -> 2, FileUtil.EmptyFileType -> 1),
        7.5
        , SourceCodeInfo("a.scala","a.scala", 10)
      )
      rf.format(codebaseInfo) shouldBe
        """
          |sbt     1
          |scala     2
          |empty-file-type     1
          |
          |---------------------------
          |avg line count: 7.5
          |longest file: a.scala    10
        """.trim.stripMargin
    }
  }
}
