package tutor

import org.scalatest.{FunSpec, ShouldMatchers}
import tutor.utils.FileUtil
import tutor.utils.FileUtil.Path

class CodebaseAnalyzerSpec extends FunSpec with ShouldMatchers {

  val ds = new CodebaseAnalyzer with DirectoryScanner with SourceCodeAnalyzer {
    override def scan(path: Path): Seq[Path] = List("a.scala", "b.scala", "c.sbt", "d")

    override def processFile(path: Path): SourceCodeInfo = path match {
      case "a.scala" => SourceCodeInfo(path, path, 10)
      case "b.scala" => SourceCodeInfo(path, path, 10)
      case "c.sbt" => SourceCodeInfo(path, path, 5)
      case "d" => SourceCodeInfo(path, path, 5)
    }
  }

  describe("CodebaseAnalyzer") {
    it("can count file numbers by type") {
      val ls = List("a.scala", "b.scala", "c.sbt", "d")
      ds.countFileTypeNum(ls) should contain theSameElementsAs Map(("scala", 2), (FileUtil.EmptyFileType, 1), ("sbt", 1))
    }
    it("can analyze avg file count") {
      ds.analyze("anypath").avgLineCount shouldBe 7.5
    }
    it("can find longest file") {
      val aInfo: SourceCodeInfo = SourceCodeInfo("a.scala", "a.scala", 10)
      val bInfo: SourceCodeInfo = SourceCodeInfo("b.scala", "b.scala", 5)
      ds.longestFile(List(aInfo, bInfo)) shouldBe aInfo
    }
    it("will return top 10 longest files"){
      val sourceCodeInfos = for (i <- 1 to 11) yield SourceCodeInfo(s"$i.scala", s"$i.scala", i)
      val top10LongFiles = ds.top10Files(sourceCodeInfos)
      top10LongFiles should have size 10
      top10LongFiles should not contain SourceCodeInfo("1.scala","1.scala",1)
    }
  }
}
