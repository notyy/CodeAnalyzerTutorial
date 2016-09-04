package tutor

import org.scalatest.{FunSpec, ShouldMatchers}
import tutor.utils.FileUtil

class DirectoryScannerSpec extends FunSpec with ShouldMatchers {
  describe("DirectoryScanner"){
    it("can scan directory recursively and return all file paths"){
      val ds = new DirectoryScanner {}
      val files = ds.scan("src/test/resources")
      files.length shouldBe 3
      FileUtil.extractLocalPath(files.head) shouldBe "sourceFileSample"
    }
  }
}
