package tutor

import java.io.{BufferedWriter, File, FileWriter, Writer}

import tutor.utils.FileUtil.Path
import tutor.utils.WriteSupport

object MainApp extends App with ReportFormatter with WriteSupport {
  if (args.length < 1) {
    println("usage: CodeAnalyzer FilePath [-oOutputfile]")
  } else {
    val path: Path = args(0)
    val file = new File(path)
    val analyzer = new CodebaseAnalyzer with DirectoryScanner with SourceCodeAnalyzer
    val rs = if (file.isFile) {
      format(analyzer.processFile(file.getAbsolutePath))
    } else {
      format(analyzer.analyze(path))
    }
    if (args.length > 1) {
      val output = args(1).drop(2)
      withWriter(output) {
        _.write(rs)
      }
      println(s"report saved into $output")
    } else {
      println(rs)
    }
  }

}
