package tutor

import org.scalatest.{FunSpec, ShouldMatchers}
import tutor.utils.FileUtil

class DirectoryScannerSpec extends FunSpec with ShouldMatchers {
  describe("DirectoryScanner"){
    it("can scan directory recursively and return all file paths" +
      " and it should only accept known txt files"){
      val ds = new DirectoryScanner {}
      val files = ds.scan("src/test/fixture", Set("scala","java"), Set("target"))
      files.length shouldBe 2
      FileUtil.extractLocalPath(files.head) shouldBe "SomeCode.scala"
    }
  }
}
