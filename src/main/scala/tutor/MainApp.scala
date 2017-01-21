package tutor

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

import com.typesafe.scalalogging.slf4j.StrictLogging
import tutor.PresetFilters.{ignoreFolders, knownFileTypes}
import tutor.utils.FileUtil.Path
import tutor.utils.{BenchmarkUtil, WriteSupport}

object MainApp extends App with ReportFormatter with WriteSupport with StrictLogging {
  if (args.length < 1) {
    println("usage: CodeAnalyzer FilePath [-oOutputfile]")
  } else {
    val path: Path = args(0)
    val file = new File(path)
    val analyzer = new CodebaseAnalyzer with DirectoryScanner with SourceCodeAnalyzer
    val rs = if (file.isFile) {
      analyzer.processFile(file.getAbsolutePath).map(format).getOrElse(s"error processing $path")
    } else {
      BenchmarkUtil.record(s"analyze code under $path") {
        analyzer.analyze(path, knownFileTypes, ignoreFolders).map(format).getOrElse("not result found")
      }
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
