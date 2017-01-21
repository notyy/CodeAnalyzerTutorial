package tutor

import java.io.File

import com.typesafe.scalalogging.slf4j.StrictLogging
import tutor.utils.FileUtil
import tutor.utils.FileUtil.Path

trait DirectoryScanner extends StrictLogging {
  /**
    * recursively scan given directory, get all file path whose ext is in knownFileTypes Set
    *
    * @param path
    * @param knownFileTypes <p>file ext, like scala, java etc. </p>
    * @return
    */
  def scan(path: Path, knownFileTypes: Set[String]): Seq[Path] = {
    logger.info(s"scanning $path for known file types $knownFileTypes")
    val files = new File(path).listFiles()
    if (files == null) {
      logger.warn(s"$path is not a legal directory")
      Vector[Path]()
    } else {
      files.foldLeft(Vector[Path]()) { (acc, f) =>
        if (f.isFile) {
          if (shouldAccept(f.getPath, knownFileTypes)) {
            logger.info(s"add file to fold ${f.getAbsolutePath}")
            acc :+ f.getAbsolutePath
          } else {
            acc
          }
        } else {
          acc ++ scan(f.getAbsolutePath, knownFileTypes)
        }
      }
    }
  }

  private def shouldAccept(path: Path, knownFileTypes: Set[String]): Boolean = {
    logger.info(s"check if should accept $path")
    knownFileTypes.contains(FileUtil.extractExtFileName(path))
  }
}
