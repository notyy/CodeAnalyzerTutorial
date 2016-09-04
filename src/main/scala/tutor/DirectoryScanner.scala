package tutor

import java.io.File

import tutor.utils.FileUtil.Path

trait DirectoryScanner {
  def scan(path: Path): Seq[Path] = {
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
