package tutor

import org.scalatest.{FunSpec, ShouldMatchers}
import tutor.utils.FileUtil

class DirectoryScannerSpec extends FunSpec with ShouldMatchers {
  describe("DirectoryScanner") {
    it("can scan directory recursively and count file numbers") {
      val ds = new DirectoryScanner
      ds.scan("src/test/resources") shouldBe Map((FileUtil.EmptyFileType, 1), ("scala", 2))
    }
    it("can merge two maps, keep their keys and sum the values"){
      val ds = new DirectoryScanner
      val map1 = Map(FileUtil.EmptyFileType -> 1, "scala" -> 1)
      val map2 = Map("scala" -> 1, "xml" -> 1)
      val merged = ds.mergeAppend(map1,map2)
      merged should have size 3
      merged("scala") shouldBe 2
      merged("xml") shouldBe 1
    }
  }
}
