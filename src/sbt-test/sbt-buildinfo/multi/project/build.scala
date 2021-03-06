import sbt._
 
object Build extends sbt.Build {
  import Keys._
  import sbtbuildinfo.Plugin._
 
  lazy val root = Project("root", file("."), settings = Defaults.defaultSettings,
    aggregate = Seq(app))
  lazy val app = Project("app", file("app"), settings = appSettings)
 
  lazy val buildSettings = Defaults.defaultSettings ++ Seq(
    version := "0.1",
    organization := "com.example",
    homepage := Some(url("http://example.com")),
    scalaVersion := "2.10.2"
  )
  
  lazy val check = TaskKey[Unit]("check")

  lazy val appSettings = buildSettings ++ buildInfoSettings ++ Seq(
    name := "sbt-buildinfo-example-app",
    sourceGenerators in Compile <+= buildInfo,
    buildInfoKeys := Seq(name,
                         projectID in "root",
                         version,
                         BuildInfoKey.map(homepage) { case (n, opt) => n -> opt.get },
                         scalaVersion),
    buildInfoPackage := "hello",
    check <<= (sourceManaged in Compile) map { (dir) =>
      val f = dir / "sbt-buildinfo" / ("%s.scala" format "BuildInfo")
      val lines = scala.io.Source.fromFile(f).getLines.toList
      lines match {
        case """package hello""" ::
             """""" ::
             """/** This object was generated by sbt-buildinfo. */""" ::
             """case object BuildInfo {""" ::
             """  /** The value is "sbt-buildinfo-example-app". */""" ::
             """  val name = "sbt-buildinfo-example-app"""" ::
             """  /** The value is "root:root:0.1-SNAPSHOT". */""" ::
             """  val projectId = "root:root:0.1-SNAPSHOT"""" ::
             """  /** The value is "0.1". */""" ::
             """  val version = "0.1"""" ::
             """  /** The value is new java.net.URL("http://example.com"). */""" ::
             """  val homepage = new java.net.URL("http://example.com")""" ::
             """  /** The value is "2.10.2". */""" ::
             """  val scalaVersion = "2.10.2"""" ::
             """  override val toString = "name: %s, projectId: %s, version: %s, homepage: %s, scalaVersion: %s" format (name, projectId, version, homepage, scalaVersion)""" ::
             "" ::
             """  val toMap: Map[String, Any] = Map[String, Any](""" ::
             """    "name" -> name,""" ::
             """    "projectId" -> projectId,""" ::
             """    "version" -> version,""" ::
             """    "homepage" -> homepage,""" ::
             """    "scalaVersion" -> scalaVersion)""" ::
             "" ::
             """  val toJson = toMap.map(i => "\""+i._1+"\":\""+i._2+"\"").mkString("{",", ","}")""" ::
             """}""" :: Nil =>
        case _ => sys.error("unexpected output: " + lines.mkString("\n"))
      }
      ()
    }
  )
}
