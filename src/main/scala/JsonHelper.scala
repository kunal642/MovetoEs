
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

import org.elasticsearch.common.xcontent.{XContentBuilder, XContentType}

object JsonHelper {

 def extractMappingFromSchema(typeName: String, tableSchema: mutable.Map[String, String]): XContentBuilder = {
   val contentBuilder: XContentBuilder = XContentBuilder.builder(XContentType.JSON.xContent)
   contentBuilder.startObject("mappings")
   contentBuilder.startObject(s"$typeName")
   contentBuilder.startObject("properties")
   for((columnName, columnType) <- tableSchema) {
     contentBuilder.startObject(columnName)
     contentBuilder.field("type", columnType)
     contentBuilder.endObject()
   }
   contentBuilder.endObject()
   contentBuilder.endObject()
   contentBuilder.endObject()
   contentBuilder
 }

  def convertRowToDocument(rowIterator: Iterator[Any], columns: Iterable[String]): XContentBuilder = {
    val contentBuilder: XContentBuilder = XContentBuilder.builder(XContentType.JSON.xContent)
    contentBuilder.startObject()
    while(rowIterator.hasNext) {
      val row = rowIterator.next().asInstanceOf[ArrayBuffer[String]]
      row.zipWithIndex.map {
        case (columnData, columnIndex) => contentBuilder
          .field(columns.toArray.apply(columnIndex), columnData)
      }
    }
    contentBuilder.endObject()
    contentBuilder
  }

}
