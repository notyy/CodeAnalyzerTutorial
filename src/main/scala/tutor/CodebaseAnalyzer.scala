package tutor

import tutor.repo.AnalyzeHistoryRepository
import tutor.utils.{BenchmarkUtil, FileUtil}
import tutor.utils.FileUtil._

import scala.math.max

object CodebaseInfo {
  def empty: CodebaseInfo = new CodebaseInfo(0, Map.empty[String, Int], 0, 0, None, Seq.empty[SourceCodeInfo])
}

case class CodebaseInfo(totalFileNums: Int, fileTypeNums: Map[String, Int], totalLineCount: Int, avgLineCount: Double, longestFileInfo: Option[SourceCodeInfo], top10Files: Seq[SourceCodeInfo]) {
  def +(sourceCodeInfo: SourceCodeInfo): CodebaseInfo = {
    val fileExt = FileUtil.extractExtFileName(sourceCodeInfo.localPath)
    val newFileTypeNums: Map[String, Int] = if (fileTypeNums.contains(fileExt)) {
      fileTypeNums.updated(fileExt, fileTypeNums(fileExt) + 1)
    } else {
      fileTypeNums + (fileExt -> 1)
    }
    val newTotalLineCount = totalLineCount + sourceCodeInfo.lineCount
    val newTotalFileNum = totalFileNums + 1
    CodebaseInfo(newTotalFileNum, newFileTypeNums, newTotalLineCount, newTotalLineCount / newTotalFileNum,
      if (longestFileInfo.isEmpty) {
        Some(sourceCodeInfo)
      } else {
        if (longestFileInfo.get.lineCount < sourceCodeInfo.lineCount) Some(sourceCodeInfo)
        else longestFileInfo
      },
      if (top10Files.isEmpty) {
        Vector(sourceCodeInfo)
      } else if (top10Files.size < 10 || sourceCodeInfo.lineCount > top10Files.last.lineCount) {
        (top10Files :+ sourceCodeInfo).sortBy(_.lineCount).reverse.take(10)
      } else {
        top10Files
      }
    )
  }
}

trait CodebaseAnalyzer extends CodebaseAnalyzerInterface {
  this: DirectoryScanner with SourceCodeAnalyzer with AnalyzeHistoryRepository=>

  override def analyze(path: Path, knownFileTypes: Set[String], ignoreFolders: Set[String]): Option[CodebaseInfo] = {
    val files = BenchmarkUtil.record("scan folders") {
      scan(path, knownFileTypes, ignoreFolders)
    }
    if (files.isEmpty) {
      None
    } else {
      val sourceCodeInfos: Seq[SourceCodeInfo] = BenchmarkUtil.record("processing each file") {
        processSourceFiles(files)
      }
      BenchmarkUtil.record("make last result ##") {
        val codebaseInfo = sourceCodeInfos.foldLeft(CodebaseInfo.empty)(_ + _)
        record(path, codebaseInfo)
        Some(codebaseInfo)
      }
    }
  }

  protected def processSourceFiles(files: Seq[Path]): Seq[SourceCodeInfo]

}
