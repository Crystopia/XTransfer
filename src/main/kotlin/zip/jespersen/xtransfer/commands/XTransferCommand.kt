package zip.jespersen.xtransfer.commands

import com.destroystokyo.paper.profile.PlayerProfile
import dev.jorel.commandapi.executors.CommandExecutor
import dev.jorel.commandapi.kotlindsl.*
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player
import zip.jespersen.xtransfer.config.ConfigManager
import zip.jespersen.xtransfer.packets.TransferUtils

object XTransferCommand {
    val mm = MiniMessage.miniMessage()

    val command = commandTree(ConfigManager.settings.commandName, "xtransfer") {
        withPermission("xtransfer.command.${ConfigManager.settings.commandName}")
        literalArgument("transfer") {
            withPermission("xtransfer.command.${ConfigManager.settings.commandName}.transfer")
            stringArgument("ip") {
                integerArgument("port") {
                    executes(
                        CommandExecutor { commandSender, commandArguments ->

                            TransferUtils.forceTransfer(
                                commandSender as Player, commandArguments[0] as String, commandArguments[1] as Int
                            )
                            commandSender.sendMessage(mm.deserialize(ConfigManager.settings.messages.successTransfer))
                        })
                }
            }
        }
        literalArgument("whitelist") {
            withPermission("xtransfer.command.${ConfigManager.settings.commandName}.whitelist")

            literalArgument("list") {
                withPermission("xtransfer.command.${ConfigManager.settings.commandName}.whitelist.list")
                anyExecutor { sender, arguments -> 
                    val players = ConfigManager.settings.whitelistedPlayerUUIDs.mapIndexed { index, string -> "${index + 1}. $string" }
                    val string = ConfigManager.settings.whitelistString
                    
                    sender.sendMessage(mm.deserialize("""
<color:#99ffb3>Whitelisted UUIDs:</color>
<gray><st>---------------</st></gray>
                        
${if (players.isEmpty()) "No whitelist" else players.joinToString("\n")}
                        
<color:#99ffb3>Whitelisted Strings:</color>
<gray><st>---------------</st></gray>
                        
${string.ifEmpty { "No whitelist" }}
                        
""".trimIndent()))
                }
            }
            literalArgument("player") {
                withPermission("xtransfer.command.${ConfigManager.settings.commandName}.whitelist.player")
                literalArgument("add") {
                    withPermission("xtransfer.command.${ConfigManager.settings.commandName}.whitelist.player.add")
                    playerProfileArgument("player") {
                        executes(
                            CommandExecutor { commandSender, commandArguments ->
                               try {
                                   ConfigManager.settings.whitelistedPlayerUUIDs.add((commandArguments.args[0] as List<PlayerProfile>)[0].uniqueId.toString())
                                   ConfigManager.save()
                                   commandSender.sendMessage(mm.deserialize(ConfigManager.settings.messages.addedNewPlayerToList))
                               } catch (e: Exception) {
                                   commandSender.sendMessage(mm.deserialize("<red>Failed to add player...</red>"))
                                   return@CommandExecutor
                               }
                            })
                    }
                }
                literalArgument("remove") {
                    withPermission("xtransfer.command.${ConfigManager.settings.commandName}.whitelist.player.remove")
                    playerProfileArgument("player") {
                        executes(
                            CommandExecutor { commandSender, commandArguments ->
                                ConfigManager.settings.whitelistedPlayerUUIDs.remove((commandArguments.args[0] as List<PlayerProfile>)[0].uniqueId.toString())
                                ConfigManager.save()
                                commandSender.sendMessage(mm.deserialize(ConfigManager.settings.messages.removePlayerFromList))
                            })
                    }
                }
            }
            literalArgument("token") {
                withPermission("xtransfer.command.${ConfigManager.settings.commandName}.whitelist.token")
                    withPermission("xtransfer.command.${ConfigManager.settings.commandName}.whitelist.player.add")
                    textArgument("token") {
                        executes(
                            CommandExecutor { commandSender, commandArguments ->
                                val token = commandArguments[0] as String

                                ConfigManager.settings.whitelistString = token
                                ConfigManager.save()

                                commandSender.sendMessage(mm.deserialize(ConfigManager.settings.messages.addedNewTokenToList))
                            })
                    }
            }
        }
    }
}