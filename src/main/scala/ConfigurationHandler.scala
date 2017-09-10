import com.typesafe.config.{Config, ConfigFactory}

object ConfigurationHandler {

  val conf: Config = ConfigFactory.load()

//  val esClusterName: String = conf.getString("es.cluster.name")

}
