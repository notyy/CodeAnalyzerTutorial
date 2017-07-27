package tutor.repo

import slick.dbio.{DBIOAction, NoStream}
import slick.jdbc.{H2Profile, JdbcProfile, OracleProfile}

import scala.concurrent.Future

trait DBConfigProvider {
  val jdbcProfile: JdbcProfile
  def run[T](action: DBIOAction[T, NoStream, Nothing]):Future[T]
}

trait OracleDB extends DBConfigProvider {
  val jdbcProfile: JdbcProfile = OracleProfile
}

trait H2DB extends DBConfigProvider {
  val jdbcProfile: JdbcProfile = H2Profile

  def run[T](action: DBIOAction[T, NoStream, Nothing]):Future[T] = {
    import jdbcProfile.api._

    val db = Database.forConfig("h2mem1")
    try {
      db.run(action)
    }finally db.close()
  }
}