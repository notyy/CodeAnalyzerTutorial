package tutor

import tutor.utils.FileUtil.Path

trait CodebaseAnalyzerSeqImpl extends CodebaseAnalyzer {
  this: DirectoryScanner with SourceCodeAnalyzer with CodebaseAnalyzeAggregator =>

  override protected def processSourceFiles(files: Seq[Path]): Seq[SourceCodeInfo] = {
    files.map(processFile).filter(_.isSuccess).map(_.get)
  }
}
