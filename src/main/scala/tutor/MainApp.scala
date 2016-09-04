package tutor

import java.io.File

import tutor.utils.FileUtil.Path

object MainApp extends App{
  if(args.length < 1){
    println("usage: CodeAnalyzer FilePath")
  }else{
    val path: Path = args(0)
    val file = new File(path)
    if(file.isFile) {
      val sourceCode = SourceCode.fromFile(path)
      println(s"name: ${sourceCode.name}      lines: ${sourceCode.count}")
    }else{
      val ds = new CodebaseAnalyzer with DirectoryScanner
      ds.countFileNum(path).foreach{
        case (fileType, count) => println(s"$fileType     $count")
      }
    }
  }
}
