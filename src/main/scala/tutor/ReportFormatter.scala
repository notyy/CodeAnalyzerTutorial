package tutor

trait ReportFormatter {
  def format(codebaseInfo: CodebaseInfo): String = {
    val longestFileInfo: Option[SourceCodeInfo] = codebaseInfo.longestFileInfo
    codebaseInfo.fileTypeNums.map {
      case (fileType, count) => s"$fileType     $count"
    }.mkString("\n") ++
      "\n" ++
      ReportFormatter.separator ++ "\n\n" ++
      s"total line count: ${codebaseInfo.totalLineCount}" ++ "\n" ++
      s"avg line count: ${codebaseInfo.avgLineCount}" ++ "\n" ++
      s"longest file: ${longestFileInfo.map(_.path).getOrElse("not avaliable")}    ${longestFileInfo.map(_.lineCount).getOrElse(0)}" ++
      "\n" ++
      ReportFormatter.separator ++ "\n\n" ++
      "top 10 long files\n" ++
      codebaseInfo.top10Files.map {
        s => s"${s.path}    ${s.lineCount}"
      }.mkString("\n")
  }

  def format(sourceCode: SourceCodeInfo): String = {
    s"name: ${sourceCode.localPath}      lines: ${sourceCode.lineCount}"
  }
}

object ReportFormatter {
  val separator = "---------------------------"
}
