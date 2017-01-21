package tutor

object PresetFilters {
  val knownFileTypes: Set[String] =
    Set("scala", "java", "txt", "xml", "json", "c", "h", "cpp", "hs", "properties","sbt","js","html")
  val ignoreFolders: Set[String] =
    Set("target","bin",".idea")
}
