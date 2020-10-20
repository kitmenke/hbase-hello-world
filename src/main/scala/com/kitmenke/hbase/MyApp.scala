package com.kitmenke.hbase

import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Delete, Get, Put, Result, Scan}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.logging.log4j.{LogManager, Logger}

object MyApp {
  lazy val logger: Logger = LogManager.getLogger(this.getClass)
  implicit def stringToBytes(s: String): Array[Byte] = Bytes.toBytes(s)

  def main(args: Array[String]): Unit = {
    logger.info("MyApp starting...")
    var connection: Connection = null
    try {
      val conf = HBaseConfiguration.create()
      conf.set("hbase.zookeeper.quorum", "CHANGE ME")
      connection = ConnectionFactory.createConnection(conf)
      lab1(connection)
      lab2(connection)
      lab2b(connection)
      lab3(connection)
      lab4(connection)
      lab5(connection)

    } catch {
      case e: Exception => logger.error("Error in main", e)
    } finally {
      if (connection != null) connection.close()
    }
  }

  def printResult(result: Result): Unit = {
    val name = Bytes.toString(result.getValue("f1", "name"))
    val username = Bytes.toString(result.getValue("f1", "username"))
    val sex = Bytes.toString(result.getValue("f1", "sex"))
    val color = Bytes.toString(result.getValue("f1", "favorite_color"))
    val mail = Bytes.toString(result.getValue("f1", "mail"))
    val bday = Bytes.toString(result.getValue("f1", "birthdate"))
    logger.debug(s"name = $name, username = $username, sex = $sex, color = $color, email = $mail, birthday = $bday")
  }

  def lab1(connection: Connection): Unit = {
    val table = connection.getTable(TableName.valueOf("kit:users"))
    val get = new Get("10000001")
    get.addColumn("f1", "mail")
    val result = table.get(get)
    printResult(result)
  }

  def lab2(connection: Connection): Unit = {
    val p = new Put("99")
    p.addColumn("f1", "username", "DE-HWE")
    p.addColumn("f1", "name", "The Panther")
    p.addColumn("f1", "sex", "F")
    p.addColumn("f1", "favorite_color", "pink")
    val table = connection.getTable(TableName.valueOf("kit:users"))
    table.put(p)
  }

  def lab2b(connection: Connection): Unit = {
    val table = connection.getTable(TableName.valueOf("kit:users"))
    val get = new Get("99")
    val result = table.get(get)
    printResult(result)
  }

  def lab3(connection: Connection): Unit = {
    val s = new Scan().withStartRow("10000001").withStopRow("10006001")
    val table = connection.getTable(TableName.valueOf("kit:users"))
    val result = table.getScanner(s)
    import scala.collection.JavaConverters._
    logger.debug("lab 3 size: " + result.iterator().asScala.size)
  }

  def lab4(connection: Connection): Unit = {
    val table = connection.getTable(TableName.valueOf("kit:users"))
    val d = new Delete("99")
    table.delete(d)
  }

  def lab5(connection: Connection): Unit = {
    val table = connection.getTable(TableName.valueOf("kit:users"))
    import scala.collection.JavaConverters._
    val gets = List("9005729", "500600", "30059640", "6005263", "800182")
      .map(key => new Get(key).addColumn("f1", "mail")).asJava
    val result: Array[Result] = table.get(gets)
    result.foreach(printResult)
  }
}
