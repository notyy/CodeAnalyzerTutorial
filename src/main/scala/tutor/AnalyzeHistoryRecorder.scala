package tutor

trait AnalyzeHistoryRecorder {
  def record(codebaseInfo: CodebaseInfo):Unit = println(s"saving to database: $codebaseInfo")
}
