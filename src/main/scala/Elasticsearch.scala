
import scala.collection.mutable

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest
import org.elasticsearch.client.transport.TransportClient

class Elasticsearch(input: InputFormat) {

  val client: TransportClient = ConnectionManager.elasticClient

  def saveToEs: Unit = {
    val tableSchema: mutable.Map[String, String] = input.getSchema
    val indexName = input.getUniqueTableName.split(",")(0)
    val typeName = input.getUniqueTableName.split(",")(1)
    val mapping = JsonHelper.extractMappingFromSchema(typeName, tableSchema)
    val mappingRequest = new PutMappingRequest().indices(indexName).`type`(typeName).source(mapping)
    client.admin().indices().putMapping(mappingRequest)
    startIndexing(indexName, typeName)
  }

  def startIndexing(indexName: String, typeName: String): Unit = {
    val inputJson = JsonHelper.convertRowToDocument(input.getData(), input.getSchema.keys)
    val response = client.prepareIndex().setSource(inputJson).get()
  }

}
