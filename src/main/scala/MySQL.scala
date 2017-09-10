import java.sql.{PreparedStatement, ResultSetMetaData, Types}

import scala.collection.mutable

class MySQL(databaseName: String, tableName: String) extends InputFormat {

  lazy val selectStmt: PreparedStatement = ConnectionManager.mySQLConnection
    .prepareStatement(s"select * from $databaseName.$tableName")
  lazy val metaData: ResultSetMetaData = selectStmt.getMetaData

  override def getUniqueTableName: String = {
    s"$databaseName.$tableName"
  }

  override def getData(): Iterator[Any] = {
    val selectResultSet = selectStmt.executeQuery()
    new Iterator[String] {
      override def hasNext: Boolean = selectResultSet.next()
      override def next(): Row = {
        val row: Row = scala.collection.mutable.ArrayBuffer.empty[String]
        for (columnNumber <- 0 to metaData.getColumnCount) {
          row.append(selectResultSet.getString(columnNumber))
        }
        row
      }
    }
  }

  override def getSchema: mutable.Map[String, String] = {
    val row: scala.collection.mutable.Map[String, String] = scala.collection.mutable.Map
      .empty[String, String]
    java.sql.Types.ARRAY
    for (columnNumber <- 0 to metaData.getColumnCount) {
      row.put(metaData.getColumnName(columnNumber), metaData.getColumnTypeName(columnNumber))
    }
    row
  }
}
