package zip.jespersen.xtransfer.events

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import zip.jespersen.xtransfer.config.ConfigManager
import zip.jespersen.xtransfer.utils.Cookie

object PlayerJoinEvent : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if (event.player.isTransferred) {
            if (!ConfigManager.settings.whitelistedPlayerUUIDs.contains(event.player.uniqueId.toString())) {
                Cookie.getCookie(event.player) { string ->
                    if ((string == null) || (string != ConfigManager.settings.whitelistString)) {
                        event.player.connection.disconnect(
                            MiniMessage.miniMessage()
                                .deserialize(ConfigManager.settings.messages.kickIfNotWhitelistedPlayerUUIDs)
                        )
                    }
                }
            }
        }
        if (ConfigManager.settings.onlyTransferJoin && !event.player.isTransferred) {
                if (!ConfigManager.settings.whitelistedPlayerUUIDs.contains(event.player.uniqueId.toString())) {
                    event.player.kick(
                        MiniMessage.miniMessage()
                            .deserialize(ConfigManager.settings.messages.kickIfNotWhitelistedPlayerUUIDs)
                    )
                }
                event.player.kick(
                    MiniMessage.miniMessage().deserialize(ConfigManager.settings.messages.kickIfOnlyTransferJoin)
                )
        }
        Cookie.storeCookie("", event.player)
    }
}