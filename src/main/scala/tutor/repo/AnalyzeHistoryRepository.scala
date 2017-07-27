package tutor.repo

import java.text.SimpleDateFormat
import java.util.Date

import tutor.{AnalyzeResultHistory, CodebaseInfo}

import scala.concurrent.Future


trait AnalyzeHistoryRepository {
  this: DBConfigProvider =>

  import Schemas._
  import jdbcProfile.api._


  def record(path: String, codebaseInfo: CodebaseInfo): Future[Int] = {
    val created = new SimpleDateFormat("yyyyMMdd").format(new Date())
    val analyzeResultHistory = AnalyzeResultHistory(path, created, codebaseInfo.toString)

    val q = analyzeResultHistories += analyzeResultHistory
    run(q)
  }
}