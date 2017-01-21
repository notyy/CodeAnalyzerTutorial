package tutor.utils

import org.scalatest.FunSpec

class BenchmarkUtilSpec extends FunSpec {
  describe("BenchmarkUtil") {
    it("records the start time and end time of an action, and calculate the elapsed time") {
      //this test is not easy to verify, so just run and look the result
      BenchmarkUtil.record("sleep") {
        Thread.sleep(100)
      }
    }
  }
}
