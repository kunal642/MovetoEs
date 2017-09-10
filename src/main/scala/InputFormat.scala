import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

trait InputFormat {

  type Row = ArrayBuffer[String]

  def getUniqueTableName: String

  def getData(): Iterator[Any]

  def getSchema: mutable.Map[String, String]

}
