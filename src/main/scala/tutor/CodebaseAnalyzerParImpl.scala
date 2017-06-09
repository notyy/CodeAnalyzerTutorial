package tutor

import tutor.utils.FileUtil.Path

trait CodebaseAnalyzerParImpl extends CodebaseAnalyzer {
  this: DirectoryScanner with SourceCodeAnalyzer with CodebaseAnalyzeAggregator =>

  override protected def processSourceFiles(files: Seq[Path]): Seq[SourceCodeInfo] = {
    files.par.map(processFile).filter(_.isSuccess).map(_.get).toVector
  }
}
