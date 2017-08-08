package tutor.utils

import java.text.SimpleDateFormat
import java.util.Date

import com.typesafe.scalalogging.StrictLogging

object BenchmarkUtil extends StrictLogging {
  def record[T](actionDesc: String)(action: => T): T = {
    val beginTime = new Date
    logger.info(s"begin $actionDesc")
    val rs = action
    logger.info(s"end $actionDesc")
    val endTime = new Date
    val elapsed = new Date(endTime.getTime - beginTime.getTime)
    val sdf = new SimpleDateFormat("mm:ss.SSS")
    logger.info(s"$actionDesc total elapsed ${sdf.format(elapsed)}")
    rs
  }
  def recordStart(actionDesc: String):Date = {
    logger.info(s"$actionDesc begin")
    new Date
  }

  def recordElapse(actionDesc: String, beginFrom: Date):Unit = {
    logger.info(s"$actionDesc ended")
    val endTime = new Date
    val elapsed = new Date(endTime.getTime - beginFrom.getTime)
    val sdf = new SimpleDateFormat("mm:ss.SSS")
    logger.info(s"$actionDesc total elapsed ${sdf.format(elapsed)}")
  }
}
