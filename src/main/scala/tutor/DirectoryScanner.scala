package tutor

import java.io.File

import com.typesafe.scalalogging.slf4j.StrictLogging
import tutor.utils.FileUtil
import tutor.utils.FileUtil.Path

import scala.annotation.tailrec

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
    logger.info(s"scanning $path for known file types $knownFileTypes")
    val files = new File(path).listFiles()
    if (files == null) {
      logger.warn(s"$path is not a legal directory")
      Vector[Path]()
    } else {
      files.foldLeft(Vector[Path]()) { (acc, f) =>
        if (f.isFile && shouldAccept(f.getPath, knownFileTypes)) {
          acc :+ f.getAbsolutePath
        } else if (f.isDirectory && (!ignoreFolders.contains(FileUtil.extractLocalPath(f.getPath)))) {
          acc ++ scan(f.getAbsolutePath, knownFileTypes, ignoreFolders)
        } else {
          acc
        }
      }
    }
  }

  private def shouldAccept(path: Path, knownFileTypes: Set[String]): Boolean = {
    logger.info(s"check if should accept $path")
    knownFileTypes.contains(FileUtil.extractExtFileName(path))
  }
}
