package tutor

import tutor.repo.AnalyzeHistoryRepository
import tutor.utils.FileUtil.Path

trait CodebaseAnalyzerSeqImpl extends CodebaseAnalyzer {
  this: DirectoryScanner with SourceCodeAnalyzer with AnalyzeHistoryRepository =>

  override protected def processSourceFiles(files: Seq[Path]): Seq[SourceCodeInfo] = {
    files.map(processFile).filter(_.isSuccess).map(_.get)
  }
}
