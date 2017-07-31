package kleisli

/**
  * Config examples
  * 
  */
object Examples {
  val good: ConfigMap = Map("http.port" -> "80",
    "http.host" -> "0.0.0.0",
    "db.url" -> "jdbc://postgres")
  val empty: ConfigMap = Map.empty

}

