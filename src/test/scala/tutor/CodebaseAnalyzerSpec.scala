package tutor

import org.scalatest.{FeatureSpec, FunSpec, GivenWhenThen, ShouldMatchers}
import tags.TestTypeTag.FunctionalTest
import tutor.utils.FileUtil
import tutor.utils.FileUtil.Path

import scala.util.{Success, Try}

class CodebaseAnalyzerSpec extends FeatureSpec with ShouldMatchers with GivenWhenThen {

  val codeBaseAnalyzer = new CodebaseAnalyzerSeqImpl with DirectoryScanner with SourceCodeAnalyzer with CodebaseAnalyzeAggregator {
    override def scan(path: Path, knowFileTypes: Set[String], ignoreFolders: Set[String]): Seq[Path] = List("a.scala", "b.scala", "c.sbt", "d")

    override def processFile(path: Path): Try[SourceCodeInfo] = path match {
      case "a.scala" => Success(SourceCodeInfo(path, path, 10))
      case "b.scala" => Success(SourceCodeInfo(path, path, 10))
      case "c.sbt" => Success(SourceCodeInfo(path, path, 5))
      case "d" => Success(SourceCodeInfo(path, path, 5))
    }
  }

  info("As a technical consultant")
  info("I want to analyze a customer's code base")
  info("So that I can find bad smell in code base more easily")

  feature("analyze source code folder and give statistic results") {
    scenario("when directory scanner returns empty, code analyzer should return None") {
      Given("a directory contains no source code file")
      val emptyCodeAnalyzer = new CodebaseAnalyzerSeqImpl with DirectoryScanner with SourceCodeAnalyzer with CodebaseAnalyzeAggregator {
        override def scan(path: Path, knowFileTypes: Set[String], ignoreFolders: Set[String]): Seq[Path] = Vector[Path]()

        override def processFile(path: Path): Try[SourceCodeInfo] = ???
      }
      When("analyze that empty directory")
      Then("it should return None(instead of broken)")
      emptyCodeAnalyzer.analyze("anypath", PresetFilters.knownFileTypes, PresetFilters.ignoreFolders) shouldBe None
    }
    scenario("when analyze a folder with source code should return correct analyze result", FunctionalTest) {
      Given("source code folder")
      //use test/fixutre as test data
      When("analyze the folder")
      val codeAnalyzer = new CodebaseAnalyzerSeqImpl with DirectoryScanner with SourceCodeAnalyzer with CodebaseAnalyzeAggregator
      val analyzeResult = codeAnalyzer.analyze("src/test/fixture", PresetFilters.knownFileTypes, PresetFilters.ignoreFolders)
      Then("it should return correct result")
      analyzeResult shouldBe 'defined
      val codeBaseInfo = analyzeResult.get
      codeBaseInfo.avgLineCount shouldBe 16.0
      val fileTypeNums = codeBaseInfo.fileTypeNums
      fileTypeNums.keySet.size shouldBe 2
      fileTypeNums("scala") shouldBe 1
      fileTypeNums("java") shouldBe 1
      codeBaseInfo.longestFileInfo.localPath shouldBe "SomeCode.scala"
      codeBaseInfo.top10Files.length shouldBe 2
      codeBaseInfo.totalLineCount shouldBe 32
      //test par implementation
      val codeAnalyzerParImpl = new CodebaseAnalyzerSeqImpl with DirectoryScanner with SourceCodeAnalyzer with CodebaseAnalyzeAggregator
      val analyzeResultOfPar = codeAnalyzerParImpl.analyze("src/test/fixture", PresetFilters.knownFileTypes, PresetFilters.ignoreFolders)
      analyzeResult shouldBe analyzeResultOfPar
      //test akka implementation
//      val codeAnalyzerAkkaImpl = new CodebaseAnalyzerAkkaImpl with DirectoryScanner
//      val analyzeResultOfAkka = codeAnalyzerAkkaImpl.analyze("src/test/fixture", PresetFilters.knownFileTypes, PresetFilters.ignoreFolders)
//      analyzeResult shouldBe analyzeResultOfAkka
    }
  }
}
