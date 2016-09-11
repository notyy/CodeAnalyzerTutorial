package tutor

trait ReportFormatter {
  def format(codebaseInfo: CodebaseInfo): String = {
    codebaseInfo.fileTypeNums.map {
      case (fileType, count) => s"$fileType     $count"
    }.mkString("\n")
  }

  def format(sourceCode: SourceCodeInfo): String = {
    s"name: ${sourceCode.name}      lines: ${sourceCode.count}"
  }
}
