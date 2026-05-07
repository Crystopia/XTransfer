package zip.jespersen.xtransfer.utils

import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import zip.jespersen.xtransfer.XTransferPlugin

object Cookie {

    var namespacedKeyForJoin = NamespacedKey(XTransferPlugin.instance, "join_token")

    fun storeCookie(cookie: String, player: Player) {
        val cookieBytes = cookie.toByteArray(Charsets.UTF_8)
        player.storeCookie(namespacedKeyForJoin, cookieBytes)
    }

    fun getCookie(player: Player, string : (cookie : String?) -> Unit){
        player.retrieveCookie(namespacedKeyForJoin).thenAccept { bytes ->
            if (bytes != null) {
                val string = String(bytes, Charsets.UTF_8)
                string(string)
            }
        }
    }
}