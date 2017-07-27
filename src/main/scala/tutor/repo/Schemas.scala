package tutor.repo

import tutor.AnalyzeResultHistory

import scala.concurrent.Future

trait Schemas {
  this: DBConfigProvider =>

  import jdbcProfile.api._

  class AnalyzeResultHistoryTable(tag: Tag) extends Table[AnalyzeResultHistory](tag, "analyze_history") {
    def path = column[String]("PATH")

    def created = column[String]("CREATED")

    def codeBaseInfo = column[String]("CODEBASE_INFO")

    def * = (path, created, codeBaseInfo) <> (AnalyzeResultHistory.tupled, AnalyzeResultHistory.unapply)
  }

  val analyzeResultHistories = TableQuery[AnalyzeResultHistoryTable]

  def setupDB(): Future[Unit] = {
    println("create tables:")
    analyzeResultHistories.schema.createStatements.foreach(println)
    val setUp = DBIO.seq(
      analyzeResultHistories.schema.create
    )
    run(setUp)
  }

  def dropDB(): Future[Unit] = {
    println("delete tables:")
    val drop = DBIO.seq(
      analyzeResultHistories.schema.drop
    )
    run(drop)
  }
}

object Schemas extends Schemas with H2DB
