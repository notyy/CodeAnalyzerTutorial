package tutor

import java.io.File

import com.typesafe.scalalogging.slf4j.StrictLogging
import tutor.utils.FileUtil.Path

trait DirectoryScanner extends StrictLogging {
  def scan(path: Path): Seq[Path] = {
    logger.info(s"scanning $path")
    val file = new File(path)
    val files = file.listFiles()
    files.foldLeft(Vector[Path]()) { (acc, f) =>
      if (f.isFile) {
        acc :+ f.getAbsolutePath
      } else {
        acc ++ scan(f.getAbsolutePath)
      }
    }
  }
}
