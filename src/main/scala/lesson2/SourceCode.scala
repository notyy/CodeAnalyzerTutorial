package lesson2

class SourceCode(val path: String, val name: String, val lines: List[String]){
  def count:Int = ???
}

object SourceCode{
  def fromStrLines(src: List[String]):SourceCode = ???
}