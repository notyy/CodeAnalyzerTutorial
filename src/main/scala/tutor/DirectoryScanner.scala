package tutor

import java.io._

import tutor.utils.FileUtil
import tutor.utils.FileUtil._


class DirectoryScanner {
  type FileType = String

  def scan(path: Path): Map[FileType, Int] = {
    val file = new File(path)
    val files = file.listFiles()
    files.foldLeft(Map[FileType, Int]()) { (acc, f) =>
      if (f.isFile) {
        val fileType: FileType = FileUtil.extractExtFileName(f.getPath)
        if (acc.contains(fileType)) {
          acc.updated(fileType, acc(fileType) + 1)
        }
        else acc + (fileType -> 1)
      } else {
        //        acc ++ scan(f.getAbsolutePath)   this is wrong!!!
        mergeAppend(acc, scan(f.getAbsolutePath))
      }
    }
  }

  def mergeAppend(map1: Map[FileType, Int], map2: Map[FileType, Int]): Map[FileType, Int] = {
    map2.foldLeft(map1) { case (m1, (m2k, m2v)) =>
      if (m1.contains(m2k)) {
        m1.updated(m2k, m1(m2k) + m2v)
      } else {
        m1 + (m2k -> m2v)
      }
    }
  }
}
