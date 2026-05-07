package zip.jespersen.xtransfer.api

import org.bukkit.entity.Player
import zip.jespersen.xtransfer.config.ConfigManager
import zip.jespersen.xtransfer.utils.Cookie

object XTransferAPI {

    fun addPlayerUuidToList(playerUuid: String) {
        ConfigManager.settings.whitelistedPlayerUUIDs.add(playerUuid)
        ConfigManager.save()
    }

    fun removePlayerUuidFromList(playerUuid: String) {
        ConfigManager.settings.whitelistedPlayerUUIDs.remove(playerUuid)
        ConfigManager.save()
    }

    fun getWhitelistToken(): String {
        return ConfigManager.settings.whitelistString
    }

    fun setWhitelistToken(token: String) {
        ConfigManager.settings.whitelistString = token
        ConfigManager.save()
    }

    fun transferPlayerWithToken(player: Player, ip: String, port: Int, token: String): Boolean {
        try {
            Cookie.storeCookie(token, player)
            player.transfer(ip, port)
            return true
        } catch (ex: Exception) {
            return false
        }
    }

}