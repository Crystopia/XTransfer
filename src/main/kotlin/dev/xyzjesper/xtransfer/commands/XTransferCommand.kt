package dev.xyzjesper.xtransfer.commands

import dev.xyzjesper.xtransfer.config.ConfigManager
import dev.xyzjesper.xtransfer.packets.TransferUtils
import dev.jorel.commandapi.executors.CommandExecutor
import dev.jorel.commandapi.kotlindsl.*
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player
import java.lang.Exception

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
                    val strings = ConfigManager.settings.whitelistString?.mapIndexed { index, string -> "${index + 1}. $string" }
                    
                    sender.sendMessage(mm.deserialize("""
<color:#99ffb3>Whitelisted UUIDs:</color>
<gray><st>---------------</st></gray>
                        
${if (players.isEmpty()) "No whitelist" else players.joinToString("\n")}
                        
<color:#99ffb3>Whitelisted Strings:</color>
<gray><st>---------------</st></gray>
                        
${if (strings!!.isEmpty()) "No whitelist" else strings.joinToString("\n")}
                        
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
                                   ConfigManager.settings.whitelistedPlayerUUIDs.add((commandArguments[0] as Player).uniqueId.toString())
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
                                ConfigManager.settings.whitelistedPlayerUUIDs.remove((commandArguments[0] as Player).uniqueId.toString())
                                ConfigManager.save()
                                commandSender.sendMessage(mm.deserialize(ConfigManager.settings.messages.removePlayerFromList))
                            })
                    }
                }
            }
            literalArgument("token") {
                withPermission("xtransfer.command.${ConfigManager.settings.commandName}.whitelist.token")
                literalArgument("add") {
                    withPermission("xtransfer.command.${ConfigManager.settings.commandName}.whitelist.player.add")
                    textArgument("token") {
                        executes(
                            CommandExecutor { commandSender, commandArguments ->
                                val token = commandArguments[0] as String

                                ConfigManager.settings.whitelistString!!.add(token)
                                ConfigManager.save()

                                commandSender.sendMessage(mm.deserialize(ConfigManager.settings.messages.addedNewTokenToList))
                            })
                    }
                }
                literalArgument("remove") {
                    withPermission("xtransfer.command.${ConfigManager.settings.commandName}.whitelist.player.remove")
                    textArgument("token") {
                        executes(
                            CommandExecutor { commandSender, commandArguments ->
                                val token = commandArguments[0] as String

                                ConfigManager.settings.whitelistString!!.remove(token)
                                ConfigManager.save()

                                commandSender.sendMessage(mm.deserialize(ConfigManager.settings.messages.removeTokenFromList))
                            })
                    }
                }
            }
        }
    }
}