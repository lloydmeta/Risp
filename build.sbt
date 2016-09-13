name := """risp"""

version := "1.1"

scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.11.8", "2.12.0-M5")

organization := "com.beachape"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"

pomExtra := (
  <url>https://github.com/lloydmeta/risp</url>
    <licenses>
      <license>
        <name>MIT</name>
        <url>http://opensource.org/licenses/MIT</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:lloydmeta/risp.git</url>
      <connection>scm:git:git@github.com:lloydmeta/risp.git</connection>
    </scm>
    <developers>
      <developer>
        <id>lloydmeta</id>
        <name>Lloyd Chan</name>
        <url>https://beachape.com</url>
      </developer>
    </developers>
  )

publishTo <<= version { v =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }