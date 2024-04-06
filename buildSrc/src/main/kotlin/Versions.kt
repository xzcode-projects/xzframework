import java.util.*

val versions = Properties().apply {
    val stream = object {}.javaClass.getResourceAsStream("versions.properties")
    load(stream)
}


