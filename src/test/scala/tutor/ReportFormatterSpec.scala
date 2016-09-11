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
        7.5
        , SourceCodeInfo("a.scala", "a.scala", 10)
        , {for (i <- 10 to 1 by -1) yield SourceCodeInfo(s"$i.scala", s"$i.scala", i)}
      )
      rf.format(codebaseInfo) shouldBe
        s"""
           |sbt     1
           |scala     2
           |empty-file-type     1
           |${ReportFormatter.separator}
           |
           |avg line count: 7.5
           |longest file: a.scala    10
           |${ReportFormatter.separator}
           |
           |top 10 long files
           |10.scala    10
           |9.scala    9
           |8.scala    8
           |7.scala    7
           |6.scala    6
           |5.scala    5
           |4.scala    4
           |3.scala    3
           |2.scala    2
           |1.scala    1
        """.trim.stripMargin
    }
  }
}
