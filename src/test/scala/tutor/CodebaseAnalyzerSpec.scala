package tutor

import org.scalatest.{FunSpec, ShouldMatchers}
import tutor.utils.FileUtil
import tutor.utils.FileUtil.Path

class CodebaseAnalyzerSpec extends FunSpec with ShouldMatchers {
  describe("CodebaseAnalyzer") {
    it("can scan directory recursively and count file numbers") {
      val ds = new CodebaseAnalyzer with DirectoryScanner with SourceCodeAnalyzer{
        override def scan(path: Path): Seq[Path] = List("a.scala","b.scala","c.sbt","d")
      }
      ds.countFileNum("any path") should contain theSameElementsAs Map(("scala", 2), (FileUtil.EmptyFileType, 1),("sbt",1))
    }
  }
}
