package dev.xyzjesper.xtransfer.events

import dev.xyzjesper.xtransfer.config.ConfigManager
import dev.xyzjesper.xtransfer.utils.Cookie
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object PlayerJoinEvent : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        Cookie.getCookie(event.player) { cookie ->
            if (event.player.isTransferred) {
                if (ConfigManager.settings.whitelistedPlayerUUIDs.contains(event.player.uniqueId.toString())) {
                    return@getCookie
                }
                if (ConfigManager.settings.whitelistString!!.contains(cookie)) {
                    if (!ConfigManager.settings.whitelistedPlayerUUIDs.contains(event.player.uniqueId.toString())) {
                        event.player.kick(
                            MiniMessage.miniMessage()
                                .deserialize(ConfigManager.settings.messages.kickIfNotWhitelistedPlayerUUIDs)
                        )
                    }
                    event.player.kick(
                        MiniMessage.miniMessage()
                            .deserialize(ConfigManager.settings.messages.kickMessageIfNotAllowedFromToJoinFromPacket)
                    )
                }

            } else if (ConfigManager.settings.onlyTransferJoin && !event.player.isTransferred) {
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


}