name := "Elasticsearch"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq("org.scalatest" % "scalatest_2.11" % "3.0.4" % "test",
  "org.elasticsearch.client" % "transport" % "5.5.2",
  "com.typesafe" % "config" % "1.2.1",
  "mysql" % "mysql-connector-java" % "6.0.6"
)
