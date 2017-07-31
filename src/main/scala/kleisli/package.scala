  import scala.util.Try
  import cats.data.Kleisli

package object kleisli {

  /**
    * Our "configuration" object. In the real world
    * this would be typesafe config or something
    */
  type ConfigMap = Map[String, String]

  /**
    * Errors reading configuration. Strings for simplicity
    */
  type ConfigError = String

  /**
    * We use `
Either`for validation.
    */
  type ConfigErrorOrA[A] = Either[ConfigError, A]

  /**
    * Kleisli arrow to read a valid `ConfigMap`
    * 
    */
  type ConfigMapReader[T] = Kleisli[ConfigErrorOrA, ConfigMap, T]

  /**
    * `ConfigMap`constructor
    * 
    */
  def ConfigMapReader[T] = Kleisli.apply[ConfigErrorOrA, ConfigMap, T] _
}
