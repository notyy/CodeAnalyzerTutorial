package tutor

object PresetFilters {
  val knownFileTypes: Set[String] =
    Set("scala", "java", "txt", "xml", "json", "c", "h", "cpp")
  val ignoreFolders: Set[String] =
    Set("target","bin")
}
