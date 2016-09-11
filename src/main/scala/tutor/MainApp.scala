package tutor

import java.io.File

import tutor.utils.FileUtil.Path

object MainApp extends App with ReportFormatter {
  if (args.length < 1) {
    println("usage: CodeAnalyzer FilePath")
  } else {
    val path: Path = args(0)
    val file = new File(path)
    val analyzer = new CodebaseAnalyzer with DirectoryScanner with SourceCodeAnalyzer
    val rs = if (file.isFile) {
      format(analyzer.processFile(file.getAbsolutePath))
    } else {
      format(analyzer.analyze(path))
    }
    println(rs)
  }

}
