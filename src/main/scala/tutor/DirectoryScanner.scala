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
    * @param ignoreFolders  <p>used to ignore output folders, like target folder for scala and java; bin fold for other languages</p>
    * @return
    */
  def scan(path: Path, knownFileTypes: Set[String], ignoreFolders: Set[String]): Seq[Path] = {
    val files = new File(path).listFiles()
    if (files == null) {
      logger.warn(s"$path is not a legal directory")
      Vector[Path]()
    } else {
      files.foldLeft(Vector[Path]()) { (acc, f) =>
        val filePath = f.getAbsolutePath
        if (f.isFile && shouldAccept(f.getPath, knownFileTypes)) {
          acc :+ filePath
        } else if (f.isDirectory && (!ignoreFolders.contains(FileUtil.extractLocalPath(f.getPath)))) {
          logger.info(s"directory $filePath scanned and added")
          acc ++ scan(filePath, knownFileTypes, ignoreFolders)
        } else {
          acc
        }
      }
    }
  }

  private def shouldAccept(path: Path, knownFileTypes: Set[String]): Boolean = {
    knownFileTypes.contains(FileUtil.extractExtFileName(path))
  }
}
