package tutor

import java.io.File

import tutor.utils.FileUtil.Path

object MainApp extends App{
  if(args.length < 1){
    println("usage: CodeAnalyzer FilePath")
  }else{
    val path: Path = args(0)
    val file = new File(path)
    val analyzer = new CodebaseAnalyzer with DirectoryScanner with SourceCodeAnalyzer
    if(file.isFile) {
      val sourceCode = analyzer.processFile(file.getAbsolutePath)
      println(s"name: ${sourceCode.name}      lines: ${sourceCode.count}")
    }else{
      analyzer.countFileNum(path).foreach{
        case (fileType, count) => println(s"$fileType     $count")
      }
    }
  }
}
