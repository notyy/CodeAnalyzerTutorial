package tutor

trait ReportFormatter {
  def format(codebaseInfo: CodebaseInfo): String = {
    val longestFileInfo: SourceCodeInfo = codebaseInfo.longestFileInfo
    codebaseInfo.fileTypeNums.map {
      case (fileType, count) => s"$fileType     $count"
    }.mkString("\n") ++
      "\n" ++
      ReportFormatter.separator ++ "\n\n" ++
      s"total line count: ${codebaseInfo.totalLineCount}" ++ "\n" ++
      s"avg line count: ${codebaseInfo.avgLineCount}" ++ "\n" ++
      s"longest file: ${longestFileInfo.localPath}    ${longestFileInfo.count}" ++
      "\n" ++
      ReportFormatter.separator ++ "\n\n" ++
      "top 10 long files\n" ++
      codebaseInfo.top10Files.map {
        s => s"${s.localPath}    ${s.count}"
      }.mkString("\n")
  }

  def format(sourceCode: SourceCodeInfo): String = {
    s"name: ${sourceCode.localPath}      lines: ${sourceCode.count}"
  }
}

object ReportFormatter {
  val separator = "---------------------------"
}
