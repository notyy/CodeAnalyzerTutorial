package tutor

import org.scalatest.{FunSpec, ShouldMatchers}
import tutor.utils.FileUtil

class ReportFormatterSpec extends FunSpec with ShouldMatchers {

  val rf = new ReportFormatter {}

  describe("ReportFormatter") {
    it("can format SourceCodeInfo") {
      rf.format(SourceCodeInfo("somepath", "some name", 10)) shouldBe "name: some name      lines: 10"
    }
    it("can format CodebaseInfo") {
      val codebaseInfo = CodebaseInfo(Map("sbt" -> 1, "scala" -> 2, FileUtil.EmptyFileType -> 1),
        totalLineCount = 15,
        avgLineCount = 7.5,
        Some(SourceCodeInfo("absolute/a.scala", "a.scala", 10)), {
          for (i <- 10 to 1 by -1) yield SourceCodeInfo(s"absolute/$i.scala", s"$i.scala", i)
        }
      )
      rf.format(codebaseInfo) shouldBe
        s"""
           |sbt     1
           |scala     2
           |empty-file-type     1
           |${ReportFormatter.separator}
           |
           |total line count: 15
           |avg line count: 7.5
           |longest file: absolute/a.scala    10
           |${ReportFormatter.separator}
           |
           |top 10 long files
           |absolute/10.scala    10
           |absolute/9.scala    9
           |absolute/8.scala    8
           |absolute/7.scala    7
           |absolute/6.scala    6
           |absolute/5.scala    5
           |absolute/4.scala    4
           |absolute/3.scala    3
           |absolute/2.scala    2
           |absolute/1.scala    1
        """.trim.stripMargin
    }
  }
}
