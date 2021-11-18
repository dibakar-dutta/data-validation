name := "data-validation"

version := "0.1"

scalaVersion := "2.12.10"

val sparkVersion = "3.0.2"
//val vegasVersion = "0.3.11"
val mysqlVersion ="5.1.42"
val postgresVersion = "42.2.2"

val akkaVersion = "2.5.26"
val akkaHttpVersion = "10.1.11"

val scalaTestVersion = "3.2.10"

resolvers ++= Seq(
  "bintray-spark-packages" at "https://dl.bintray.com/spark-packages/maven",
  "Typesafe Simple Repository" at "https://repo.typesafe.com/typesafe/simple/maven-releases",
  "MavenRepository" at "https://mvnrepository.com"
)


libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  // logging
  "org.apache.logging.log4j" % "log4j-api" % "2.4.1",
  "org.apache.logging.log4j" % "log4j-core" % "2.4.1",
  // postgres for DB connectivity
  "org.postgresql" % "postgresql" % postgresVersion,

  // postgres for DB connectivity
  "mysql" % "mysql-connector-java" % mysqlVersion

) ++ cucumberDependencies ++ akkaHttpDependencies ++ scalaTest ++ amazonDeequ

lazy val cucumberDependencies = Seq(
  "io.cucumber" %% "cucumber-scala" % "8.0.0" % Test,
  "io.cucumber" % "cucumber-junit" % "7.0.0" % Test,
  "junit" % "junit" % "4.13.2",
  "com.novocode" % "junit-interface" % "0.11" % Test
)

lazy val akkaHttpDependencies = Seq(

  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,

  // akka streams
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  // akka http
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
)

lazy val scalaTest = Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
)

lazy val amazonDeequ = Seq("com.amazon.deequ" % "deequ" % "2.0.0-spark-3.1")

