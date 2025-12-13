package dev.xyzjesper.xtransfer.utils

import dev.xyzjesper.xtransfer.XTransferPlugin
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player

object Cookie {

    var namespacedKeyForJoin = NamespacedKey(XTransferPlugin.instance, "joinKey")

    fun storeCookie(cookie: String, player: Player) {
        val cookieBytes = cookie.toByteArray(Charsets.UTF_8)
        player.storeCookie(namespacedKeyForJoin, cookieBytes)
    }

    fun getCookie(player: Player, callback: (String?) -> Unit) {
        player.retrieveCookie(namespacedKeyForJoin).thenAccept { bytes ->
            if (bytes == null) {
                return@thenAccept
            }
            val string = String(bytes, Charsets.UTF_8)
            callback(string)
        }.exceptionally {
            it.printStackTrace()
            callback(null)
            null
        }
    }
}