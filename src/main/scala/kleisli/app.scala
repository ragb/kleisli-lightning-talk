package kleisli


import cats.data._
import cats.instances.either._



/**
  * Application class.
  * In the real world this would have code for startup and such
  * 
  */
final case class App(db: DbService, http: HttpService)

object App {
  // Since the `AppConfig`is assumed to be valid
  // we use the `Reader` type
  // the local invokation is used to get a `DbConfig` from the input `AppConfig`
  val fromAppConfig: Reader[AppConfig, App] = for {
    dbService <- DbService.fromDbConfig.local[AppConfig](_.db)
httpService <- HttpService.fromHttpConfig.local[AppConfig](_.http)
  } yield App(dbService, httpService)

  // Since reading from a map needs validation,
  // we need to lift our reader to the
  // `ConfigErrorOrA` carrier type.
  val fromMap: ConfigMapReader[App] = AppConfig.fromMap andThen
  fromAppConfig.lift[ConfigErrorOrA]
}

/**
  * Dumb DB service
  * 
  */
final case class DbService()

object DbService {
  // Here we ignore the configuration since our DB service does nothing
  val fromDbConfig = Reader[DbConfig, DbService](_ => DbService())
}

/**
  * Dumb http service
  * 
  */
final case class HttpService()

object HttpService {
  val fromHttpConfig = Reader[HttpConfig, HttpService](_ => HttpService())
}


