import _root_.bintray.Keys._
import _root_.bintray.Plugin._
import _root_.sbtrelease.ReleasePlugin._

sbtPlugin := true

name := "sbt-buildinfo"

organization := "com.yetu"

version := "0.3.3-yetu"

// sbtVersion in Global := "0.13.0" 

// scalaVersion in Global := "2.10.2"

scalacOptions := Seq("-unchecked", "-deprecation")

description := "sbt plugin to generate build info"

publishArtifact in (Compile, packageBin) := true

publishArtifact in (Test, packageBin) := false

publishArtifact in (Compile, packageDoc) := true

publishArtifact in (Compile, packageSrc) := true

publishMavenStyle := true

// sbt-release plugin settings:
releaseSettings

// settings for bintray publishing

bintrayPublishSettings

repository in bintray := "maven"

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

packageLabels in bintray := Seq("yetu")

bintrayOrganization in bintray := Some("yetu")
