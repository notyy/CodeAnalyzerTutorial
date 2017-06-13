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
    scan(path)(Vector[Path](), ignoreFolders) {
      (acc, f) =>
        val filePath = f.getAbsolutePath
        if (f.isFile && shouldAccept(f.getPath, knownFileTypes)) {
          acc :+ filePath
        } else acc
    }
  }

  def scan[T](path: Path)(initValue: T, ignoreFolders: Set[String])(processFile: (T, File) => T): T = {
    val files = new File(path).listFiles()
    if (files == null) {
      logger.warn(s"$path is not a legal directory")
      initValue
    } else {
      files.foldLeft(initValue) { (acc, file) =>
        val filePath = file.getAbsolutePath
        if (file.isFile) {
          processFile(acc, file)
        } else if (file.isDirectory && (!ignoreFolders.contains(FileUtil.extractLocalPath(file.getPath)))) {
          scan(filePath)(acc, ignoreFolders)(processFile)
        } else {
          acc
        }
      }
    }
  }

  def foreachFile(path: Path, knownFileTypes: Set[String], ignoreFolders: Set[String])(processFile: File => Unit): Unit = {
    scan(path)((), ignoreFolders) {
      (acc, f) =>
        val filePath = f.getAbsolutePath
        if (f.isFile && shouldAccept(f.getPath, knownFileTypes)) {
          processFile(f)
        } else ()
    }
  }

  private def shouldAccept(path: Path, knownFileTypes: Set[String]): Boolean = {
    knownFileTypes.contains(FileUtil.extractExtFileName(path))
  }
}
