import java.net.InetAddress
import java.sql.{Connection, DriverManager}

import com.typesafe.config.Config
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.transport.client.PreBuiltTransportClient


object ConnectionManager {

  def elasticClient: TransportClient = {
    val esConfiguration: Config = ConfigurationHandler.conf.getConfig("es")
    val settings: Settings = Settings.builder()
      .put("cluster.name", esConfiguration.getString("es.cluster.name")).build()
    new PreBuiltTransportClient(settings)
      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300))
  }

  def mySQLConnection: Connection = {
    Class.forName("com.mysql.jdbc.Driver").newInstance
      DriverManager.getConnection("jdbc:mysql://localhost/test?" +
                                  "user=root&password=kunal")
  }

}
