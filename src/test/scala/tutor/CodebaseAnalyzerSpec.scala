package tutor

import org.scalatest.{FeatureSpec, FunSpec, GivenWhenThen, ShouldMatchers}
import tutor.utils.FileUtil
import tutor.utils.FileUtil.Path

import scala.util.{Success, Try}

class CodebaseAnalyzerSpec extends FeatureSpec with ShouldMatchers with GivenWhenThen {

  val codeBaseAnalyzer = new CodebaseAnalyzerSeqImpl with DirectoryScanner with SourceCodeAnalyzer {
    override def scan(path: Path, knowFileTypes: Set[String], ignoreFolders: Set[String]): Seq[Path] = List("a.scala", "b.scala", "c.sbt", "d")

    override def processFile(path: Path): Try[SourceCodeInfo] = path match {
      case "a.scala" => Success(SourceCodeInfo(path, path, 10))
      case "b.scala" => Success(SourceCodeInfo(path, path, 10))
      case "c.sbt" => Success(SourceCodeInfo(path, path, 5))
      case "d" => Success(SourceCodeInfo(path, path, 5))
    }
  }

  val aInfo: SourceCodeInfo = SourceCodeInfo("a.scala", "a.scala", 10)
  val bInfo: SourceCodeInfo = SourceCodeInfo("b.scala", "b.scala", 5)

  info("As a technical consultant")
  info("I want to analyze a customer's code base")
  info("So that I can find bad smell in code base more easily")

  feature("Analyze a folder containing source code") {
    scenario("count file numbers by type") {
      Given("a folder contains 2 .scala file and a .sbt file and a file without ext")
      val ls = List("a.scala", "b.scala", "c.sbt", "d")
      When("analyze that folder")
      Then("result should contain 2 scala file, 1 sbt file and 1 empty-type file")
      codeBaseAnalyzer.countFileTypeNum(ls) should contain theSameElementsAs Map[String, Int](("scala", 2), (FileUtil.EmptyFileType, 1), ("sbt", 1))
    }
    scenario("analyze avg file count") {
      Given("a.scala: 10 lines, b.scala: 10 lines, c.sbt: 5 lines, d: 5 lines")
      When("calculte avg file count")
      Then("result should be 7.5")
      codeBaseAnalyzer.analyze("anypath", PresetFilters.knownFileTypes, PresetFilters.ignoreFolders).get.avgLineCount shouldBe 7.5
    }
    scenario("find longest file") {
      Given("a.scala: 10 lines, b.scala: 5 lines")
      When("finding longest file")
      Then("a.scala should be returned")
      codeBaseAnalyzer.longestFile(List(aInfo, bInfo)) shouldBe aInfo
    }
    scenario("find top 10 longest files") {
      Given("11 files whose line of code is 1 to 11")
      val sourceCodeInfos = for (i <- 1 to 11) yield SourceCodeInfo(s"$i.scala", s"$i.scala", i)
      When("finding top 10 longest files")
      val top10LongFiles = codeBaseAnalyzer.top10Files(sourceCodeInfos)
      Then("10 files should be returned")
      top10LongFiles should have size 10
      And("result should not contains file whose line of code 1")
      top10LongFiles should not contain SourceCodeInfo("1.scala", "1.scala", 1)
    }
    scenario("count total line numbers") {
      Given("a.scala: 10 lines, b.scala: 5")
      When("count total line numbers")
      Then("total line numbers should be 15")
      codeBaseAnalyzer.totalLineCount(List(aInfo, bInfo)) shouldBe 15
    }
    scenario("when directory scanner returns empty, code analyzer should return None") {
      Given("a directory contains no source code file")
      val emptyCodeAnalyzer = new CodebaseAnalyzerSeqImpl with DirectoryScanner with SourceCodeAnalyzer {
        override def scan(path: Path, knowFileTypes: Set[String], ignoreFolders: Set[String]): Seq[Path] = Vector[Path]()

        override def processFile(path: Path): Try[SourceCodeInfo] = ???
      }
      When("analyze that empty directory")
      Then("it should return None(instead of broken)")
      emptyCodeAnalyzer.analyze("anypath", PresetFilters.knownFileTypes, PresetFilters.ignoreFolders) shouldBe None
    }
  }
}
