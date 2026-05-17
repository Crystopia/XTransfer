# XTransfer

## Features
- Developer API
- Whitelist of Player UUID's
- Token Whitelist via Paper's Cookie System.
- Customizable and developer-friendly

## Commands
- `/xtransfer` (or your custom Option) - Permission: `transferpacket.command.<commandName>`

## Permissions
- `xtransfer.command.<commandName>.transfer`
- `xtransfer.command.<commandName>.whitelist`
- `xtransfer.command.<commandName>.whitelist.list`
- `xtransfer.command.<commandName>.whitelist.player`
- `xtransfer.command.<commandName>.whitelist.player.add`
- `xtransfer.command.<commandName>.whitelist.player.remove`
- `xtransfer.command.<commandName>.whitelist.token`
- `xtransfer.command.<commandName>.whitelist.token.add`
- `xtransfer.command.<commandName>.whitelist.token.remove`

## Developer API

**Use our repo to Download the API**
- [https://repo.jespersen.zip/#/releases/dev/xyzjesper/xtransfer](https://repo.jespersen.zip/#/releases/dev/xyzjesper/xtransfer)

### Project Setup

To access the API use the `XTransferAPI` Menthods and interact with the plugin.
__Note: Please add the Plugin TransferPacket as depend! And check the enabled status.__

```kts
repositories {
    mavenCentral()
    maven("https://repo.jespersen.zip/releases")
}

dependencies {
    compileOnly("zip.jespersen:xtransfer:<version>")
}

```
