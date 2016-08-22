package lesson1.hello

import org.scalatest.{FunSpec, ShouldMatchers}

class MainTest extends FunSpec with ShouldMatchers{
  describe("Main"){
    it("can add x and y"){
      Main.add(1,2) shouldBe 3
    }
  }
}
