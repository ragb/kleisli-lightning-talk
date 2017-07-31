package kleisli

import scala.util.Either

import cats.data.Kleisli
import cats.implicits._

/**
  * Basic config utilities
  * 
  */
object Config {

  /**
    * Constructs a kleisli arrow that reads a String from a config map if exists
    * 
    */
  def readString(key: String) = ConfigMapReader[String] {config =>
    Either.fromOption(config.get(key),
    s"$key not found")
  }

  /**
    * Constructs a kleisli arrow that gets an integer from a config map
    * 
    */
  def readInt(key: String) = readString(key) flatMapF {str =>
    Either.catchNonFatal(str.toInt)
    .leftMap(t => s"Error reading key $key, ${t.getMessage()}")
  }
}

/**
  * Http service configuration
  * 
  */
final case class HttpConfig(host: String, port: Int)

object HttpConfig {
  import Config._


  // note that if our `ConfigErrorOrA`type was a `Validated`
  // this won't work
  // `Validated`would be actually a better option
// since we could have multiple errors
  val fromMap: ConfigMapReader[HttpConfig] = for {
    host <- readString("http.host")
    port <- readInt("http.port")
  } yield HttpConfig(host, port)
}


/**
  * Database configuration
  * 
  */
final case class DbConfig(url: String)

object DbConfig {
  import Config._

  val fromMap: ConfigMapReader[DbConfig] = readString("db.url")
    .map(DbConfig.apply _)
}


/**
  * Application configuration.
  * 
  */
final case class AppConfig(db: DbConfig, http: HttpConfig)

object AppConfig {
  // Note that the application configuration is constructed just
  // based on kleisli arrow composition, as almost all
  // stuff here.
  val fromMap: ConfigMapReader[AppConfig] = for {
    http <- HttpConfig.fromMap
db <- DbConfig.fromMap
  } yield AppConfig(db, http)
}
