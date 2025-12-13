package dev.xyzjesper.xtransfer.packets

import dev.xyzjesper.xtransfer.config.ConfigManager
import dev.xyzjesper.xtransfer.utils.Cookie
import org.bukkit.entity.Player


object TransferUtils {
    fun forceTransfer(player: Player, ip: String, port: Int) {
        Cookie.storeCookie(ConfigManager.settings.whitelistString as String, player)
        player.transfer(ip, port)
    }
}