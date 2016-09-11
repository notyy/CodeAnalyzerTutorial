package tutor

trait ReportFormatter {
  val separator = "---------------------------"
  def format(codebaseInfo: CodebaseInfo): String = {
    codebaseInfo.fileTypeNums.map {
      case (fileType, count) => s"$fileType     $count"
    }.mkString("\n") ++
    "\n\n" ++
    separator ++ "\n" ++
    s"avg line count: ${codebaseInfo.avgLineCount}"

  }

  def format(sourceCode: SourceCodeInfo): String = {
    s"name: ${sourceCode.localPath}      lines: ${sourceCode.count}"
  }
}
