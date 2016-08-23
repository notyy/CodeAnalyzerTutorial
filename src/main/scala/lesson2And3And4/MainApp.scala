package lesson2And3And4

object MainApp extends App{
  if(args.length < 1){
    println("usage: CodeAnalyzer FilePath")
  }else{
    val sourceCode = SourceCode.fromFile(args(0))
    println(s"name: ${sourceCode.name}      lines: ${sourceCode.count}")
  }
}
