package tutor

import tutor.repo.AnalyzeHistoryRepository
import tutor.utils.FileUtil.Path

trait CodebaseAnalyzerParImpl extends CodebaseAnalyzer {
  this: DirectoryScanner with SourceCodeAnalyzer with AnalyzeHistoryRepository =>

  override protected def processSourceFiles(files: Seq[Path]): Seq[SourceCodeInfo] = {
    files.par.map(processFile).filter(_.isSuccess).map(_.get).toVector
  }
}
