package zip.jespersen.xtransfer.config

interface Configurable {
    fun save()
    fun load() {}
    fun reset() {}
}