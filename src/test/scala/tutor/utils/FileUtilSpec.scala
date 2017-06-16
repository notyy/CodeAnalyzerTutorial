package tutor.utils

import org.scalatest.{FunSpec, Matchers}

class FileUtilSpec extends FunSpec with Matchers {
  describe("FileUtil"){
    it("can extract file extension name"){
      val path = "src/test/build.sbt"
      FileUtil.extractExtFileName(path) shouldBe "sbt"
    }
    it("if file has no extension name, should give EmptyFileType constant"){
      val path = "src/test/build"
      FileUtil.extractExtFileName(path) shouldBe FileUtil.EmptyFileType
    }
    it("can extract local file path"){
      val path = "src/test/build.sbt"
      FileUtil.extractLocalPath(path) shouldBe "build.sbt"
    }
  }
}
