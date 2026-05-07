package zip.jespersen.xtransfer

import zip.jespersen.xtransfer.commands.XTransferCommand
import zip.jespersen.xtransfer.utils.Log
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIPaperConfig
import zip.jespersen.xtransfer.events.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin

class XTransferPlugin : JavaPlugin() {

    companion object {
        lateinit var instance: XTransferPlugin
    }

    init {
        instance = this
    }

    override fun onLoad() {
        CommandAPI.onLoad(CommandAPIPaperConfig(this).verboseOutput(false).silentLogs(false))
        Log.info("Loading Plugin XTransfer")
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