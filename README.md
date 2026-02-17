<h1 align="center">🎮 GuildChat Shortener</h1>

<p align="center">
  <b>Fabric mod to shorten and customize Discord bridge messages in Hypixel guilds</b>
</p>

<p align="center">
  <a href="https://www.minecraft.net/"><img src="https://img.shields.io/badge/Minecraft-1.21-brightgreen.svg" alt="Minecraft"></a>
  <a href="https://fabricmc.net/"><img src="https://img.shields.io/badge/Fabric-0.18.4-orange.svg" alt="Fabric"></a>
  <a href="https://www.oracle.com/java/"><img src="https://img.shields.io/badge/Java-21-blue.svg" alt="Java"></a>
  <a href="./LICENSE"><img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="License"></a>
</p>

<p align="center">
  <b>English</b> | <a href="README.fr.md">Français</a>
</p>

---

## 📖 Description

**GuildChat Shortener** is a Fabric client-side mod that transforms long Discord bridge messages in your Hypixel guild into short and elegant messages. No more cluttered chat - customize the bridge display with colors and aliases to your liking!

### ✨ Key Features

- 🤖 **Automatic Detection** - The mod automatically detects Discord bridge messages
- 🎨 **Color Customization** - Change the colors of the bridge alias and player names
- 🏷️ **Custom Aliases** - Replace the bot name with a short alias (e.g., "Discord", "Bridge", "DC")
- ⚙️ **Flexible Configuration** - Intuitive in-game commands to adjust settings
- 🔄 **Smart Detection** - Automatically recognizes Discord bridge messages
- 💾 **Automatic Saving** - Your configuration persists between sessions
- 🌐 **Global Mode** - Enable formatting for all guild messages

---

## 📥 Installation

1. **Prerequisites**:
   - Minecraft **1.21** or higher
   - [Fabric Loader](https://fabricmc.net/use/) **0.18.0** or higher
   - [Fabric API](https://modrinth.com/mod/fabric-api)
   - Java **21** or higher

2. **Installation**:
   - Download the `.jar` file from the [Releases](https://github.com/Tjiba/GuildChatShortener/releases) page
   - Place the file in your `mods/` folder
   - Launch Minecraft with the Fabric profile

---

## 🎯 Usage

### Automatic Startup

**The mod automatically detects bridge messages!** You don't need to configure anything - formatting activates automatically as soon as a Discord bridge message is detected in your guild.

### Manual Configuration (Optional)

If automatic detection doesn't work, you can manually configure the Discord bot name:

```
/bridgesetup <mcName> <alias>
```

**Example**:
```
/bridgesetup DiscordBot DC
```

### Main Commands

| Command | Description | Example |
|---------|-------------|---------|
| `/bridge status` | Display current configuration | - |
| `/bridge reset` | Reset entire configuration | - |
| `/bridge help` | Display quick help | - |

### 🏷️ Alias Management

| Command | Description | Example |
|---------|-------------|---------|
| `/bridgename <alias>` | Change bridge alias | `/bridgename Discord` |
| `/bridgename reset` | Reset to default alias ("Bridge") | - |

### 🎨 Color Customization

| Command | Alias | Description |
|---------|-------|-------------|
| `/bridgecolor <color>` | `/bc <color>` | Change alias color |
| `/bridgecolor reset` | `/bc reset` | Reset to default cyan |
| `/bridgeplayercolor <color>` | `/bpc <color>` | Change player name color |
| `/bridgeplayercolor reset` | `/bpc reset` | Reset to default dark cyan |

**Examples**:
```
/bc yellow         # Bridge alias in yellow
/bpc green         # Player name in green
/bridgecolor &e    # Alias in yellow (color code)
```

### 🌐 Global Mode

Enable or disable formatting for **all** guild messages:

```
/bridgeactivateall       # Enable global mode
/bridgeactivateall off   # Disable global mode
```

---

## 🎨 Available Colors

The `<color>` parameter accepts French/English names or Minecraft codes (`&` or `§`):

| Name | Code | Preview |
|------|------|---------|
| `noir` / `black` | `&0` | Black |
| `bleu fonce` / `dark_blue` | `&1` | Dark Blue |
| `vert fonce` / `dark_green` | `&2` | Dark Green |
| `cyan fonce` / `dark_aqua` | `&3` | Dark Cyan ⭐ (default player) |
| `rouge fonce` / `dark_red` | `&4` | Dark Red |
| `violet fonce` / `dark_purple` | `&5` | Dark Purple |
| `or` / `gold` | `&6` | Gold |
| `gris` / `gray` | `&7` | Gray |
| `gris fonce` / `dark_gray` | `&8` | Dark Gray |
| `bleu` / `blue` | `&9` | Blue |
| `vert` / `green` | `&a` | Green |
| `cyan` / `aqua` | `&b` | Cyan ⭐ (default bridge) |
| `rouge` / `red` | `&c` | Red |
| `rose clair` / `light_purple` | `&d` | Light Purple |
| `jaune` / `yellow` | `&e` | Yellow |
| `blanc` / `white` | `&f` | White |

---

## 🛠️ Development

### Build the project

```cmd
gradlew.bat build
```

The compiled JAR file can be found in `build/libs/GuildChatShortener-1.0.0.jar`.

### Project Structure

```
src/main/
├── java/com/guildchat/formatter/
│   ├── GuildChatMod.java          # Main entry point
│   ├── BridgeConfig.java          # Configuration management
│   └── mixin/
│       └── ChatHudMixin.java      # Injection to format messages
└── resources/
    ├── fabric.mod.json            # Mod metadata
    └── guildchat-formatter.mixins.json
```

---

## 📝 Changelog

See [CHANGELOG.md](CHANGELOG.md) for version history.

---

## 🤝 Contributing

Contributions are welcome! Feel free to:
- 🐛 Report bugs
- 💡 Suggest new features
- 🔧 Submit pull requests

---

## 📜 License

This project is licensed under the **MIT** License. See the [LICENSE](./LICENSE) file for details.

---

## 👤 Author

Developed with ❤️ by **[Tjiba](https://github.com/Tjiba)** (MeteoFrance in-game)

---

<h3 align="center">⭐ If this mod is useful to you, feel free to give it a star!</h3>

<p align="center">
  <a href="https://github.com/Tjiba/GuildChatShortener/releases">Download</a> • 
  <a href="https://github.com/Tjiba/GuildChatShortener/issues">Report a Bug</a> • 
  <a href="https://github.com/Tjiba/GuildChatShortener/issues">Request a Feature</a>
</p>
