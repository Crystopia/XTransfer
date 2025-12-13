package dev.xyzjesper.xtransfer.config

interface Configurable {
    fun save()
    fun load() {}
    fun reset() {}
}