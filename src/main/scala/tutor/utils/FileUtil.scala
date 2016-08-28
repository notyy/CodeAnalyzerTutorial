package tutor.utils

object FileUtil {
  type Path = String
  val EmptyFileType = "empty-file-type"

  def extractExtFileName(file: Path): String = {
    val localPath = extractLocalPath(file)
    if (localPath.contains(".")) {
      localPath.split("\\.").last
    } else EmptyFileType
  }

  def extractLocalPath(path: Path): String = {
    path.split("/").last
  }
}
