package tutor.utils

import java.io.{BufferedWriter, File, FileWriter, Writer}

trait WriteSupport {

  def withWriter(path: String)(f: Writer => Unit): Unit ={
    var writer: Writer = null
    try {
      val file = new File(path)
      if (!file.exists()) file.createNewFile()
      writer = new BufferedWriter(new FileWriter(file))
      f(writer)
      writer.flush()
    } finally {
      if (writer != null) writer.close()
    }
  }
}
