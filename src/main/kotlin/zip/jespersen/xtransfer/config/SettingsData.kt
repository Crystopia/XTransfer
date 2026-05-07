package zip.jespersen.xtransfer.config

import kotlinx.serialization.Serializable

@Serializable
data class SettingsData(
    var commandName: String = "xtransfer",
    var messages: Messages,
    var whitelistString: String = "yqKJNgQtoqn07hIAeh3L7jQYa",
    var onlyTransferJoin: Boolean = false,
    var whitelistedPlayerUUIDs: MutableList<String> = mutableListOf(),
)

@Serializable
data class Messages(
    var successTransfer: String = "<green>Successfully transfer from the server!</green>",
    var kickMessageIfNotAllowedFromToJoinFromPacket: String = "<red>You don't can you via Transfer Packets because you are not Whitelisted</red>",
    var kickIfOnlyTransferJoin: String = "<red>You only can join with Transfer Packets!</red>",
    var kickIfNotWhitelistedPlayerUUIDs: String = "<red>You are not Whitelisted</red>",
    var addedNewTokenToList: String = "<green>Added token to whitelist</green>",
    var addedNewPlayerToList: String = "<green>Added player to whitelist</green>",
    var removePlayerFromList: String = "<red>Removed from list</red>",
    var removeTokenFromList: String = "<red>Removed token from token</red>",
)
