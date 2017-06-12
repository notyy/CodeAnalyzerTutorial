package tutor
import tutor.utils.FileUtil.Path

trait CodebaseAnalyzerInterface {

  def analyze(path: Path, knownFileTypes: Set[String], ignoreFolders: Set[String]): Option[CodebaseInfo]
}
