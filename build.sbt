name := """tutorial-play-java-2.5"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

sources in (Compile, playEnhancerGenerateAccessors) := {
  ((javaSource in Compile).value / "models" ** "*.java").get
}

scalaVersion := "2.11.8"

libraryDependencies += javaJpa

libraryDependencies += filters

libraryDependencies += cache

libraryDependencies += javaWs

libraryDependencies += evolutions

// https://mvnrepository.com/artifact/dom4j/dom4j
libraryDependencies += "dom4j" % "dom4j" % "1.6"

libraryDependencies += "org.mockito" % "mockito-core" % "2.1.0"

libraryDependencies += javaWs % "test"

// Security and authetication libraries
libraryDependencies += "be.objectify" %% "deadbolt-java" % "2.5.3"

// https://mvnrepository.com/artifact/org.hibernate/hibernate-core
// must exclude dom4j in hibernate core because it causes staxeventreader exceptions
// http://stackoverflow.com/questions/36222306/caused-by-java-lang-classnotfoundexception-org-dom4j-io-staxeventreader
libraryDependencies += "org.hibernate" % "hibernate-core" % "5.2.3.Final" exclude("dom4j", "dom4j") exclude("javax.transaction", "jta") exclude("org.slf4j", "slf4j-api")

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.36"

//Metamodel generator
// libraryDependencies += "org.hibernate" % "hibernate-jpamodelgen" % "5.2.3.Final"

//Metamodel generator
// javacOptions += "-s"

//Metamodel generator
// javacOptions += "app"

