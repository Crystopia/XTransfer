package zip.jespersen.xtransfer.packets

import org.bukkit.entity.Player
import zip.jespersen.xtransfer.config.ConfigManager
import zip.jespersen.xtransfer.utils.Cookie


object TransferUtils {
    fun forceTransfer(player: Player, ip: String, port: Int) {
        Cookie.storeCookie(ConfigManager.settings.whitelistString, player)
        player.transfer(ip, port)
    }
}