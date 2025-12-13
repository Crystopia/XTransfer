package dev.xyzjesper.xtransfer

import dev.xyzjesper.xtransfer.commands.XTransferCommand
import dev.xyzjesper.xtransfer.utils.Log
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIPaperConfig
import dev.xyzjesper.xtransfer.events.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin

class XTransferPlugin : JavaPlugin() {

    companion object {
        lateinit var instance: XTransferPlugin
    }

    init {
        instance = this
    }

    override fun onLoad() {
        CommandAPI.onLoad(CommandAPIPaperConfig(this).verboseOutput(true))
        Log.info("Loading Plugin...")
    }

    override fun onEnable() {
        CommandAPI.onEnable()

        // Commands
        XTransferCommand

        //Events
        server.pluginManager.registerEvents(PlayerJoinEvent, this)

        Log.info("Plugin enabled!")
    }

    override fun onDisable() {
        CommandAPI.onDisable()

        Log.info("Plugin disabled!")
    }

}