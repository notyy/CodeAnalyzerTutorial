package tutor

import org.scalatest.{FunSpec, ShouldMatchers}
import tutor.utils.FileUtil

class CodebaseAnalyzerSpec extends FunSpec with ShouldMatchers {
  describe("CodebaseAnalyzer") {
    it("can scan directory recursively and count file numbers") {
      val ds = new CodebaseAnalyzer with DirectoryScanner
      ds.countFileNum("src/test/resources") shouldBe Map((FileUtil.EmptyFileType, 1), ("scala", 2))
    }
  }
}
