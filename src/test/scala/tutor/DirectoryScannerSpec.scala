package tutor

import java.io.File

import org.scalatest.{FunSpec, Matchers}
import tags.TestTypeTag.FunctionalTest
import tutor.utils.FileUtil

import scala.collection.mutable.ArrayBuffer

class DirectoryScannerSpec extends FunSpec with Matchers {
  describe("DirectoryScanner") {
    it("can scan directory recursively and return all file paths" +
      " and it should only accept known txt files", FunctionalTest) {
      val ds = new DirectoryScanner {}
      val files = ds.scan("src/test/fixture", Set("scala", "java"), Set("target"))
      files.length shouldBe 2
      FileUtil.extractLocalPath(files.head) shouldBe "SomeCode.scala"
    }
    it("can scan directory recursively and let user do what they want on the file, " +
      "and it should only accept known txt files", FunctionalTest){
      val ds = new DirectoryScanner {}
      val files:ArrayBuffer[File]  = new ArrayBuffer[File]()
      ds.foreachFile("src/test/fixture", Set("scala", "java"), Set("target")){ file =>
        files += file
      }
      files.length shouldBe 2
      FileUtil.extractLocalPath(files.head.getAbsolutePath) shouldBe "SomeCode.scala"
    }
  }
}
