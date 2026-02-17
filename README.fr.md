<div style="text-align: center;">

# 🎮 GuildChat Shortener

**Mod Fabric pour raccourcir et personnaliser les messages du bridge Discord dans les guildes Hypixel**

![France](https://img.shields.io/badge/Made%20in-France-blue?style=flat&logo=data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iOTAwIiBoZWlnaHQ9IjYwMCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KICA8cmVjdCB3aWR0aD0iOTAwIiBoZWlnaHQ9IjYwMCIgZmlsbD0iI0VEMjkzOSIvPgogIDxyZWN0IHdpZHRoPSI2MDAiIGhlaWdodD0iNjAwIiBmaWxsPSIjRkZGIi8+CiAgPHJlY3Qgd2lkdGg9IjMwMCIgaGVpZ2h0PSI2MDAiIGZpbGw9IiMwMDIzOTUiLz4KPC9zdmc+) **Fièrement créé en France** ![France](https://img.shields.io/badge/Made%20in-France-blue?style=flat&logo=data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iOTAwIiBoZWlnaHQ9IjYwMCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KICA8cmVjdCB3aWR0aD0iOTAwIiBoZWlnaHQ9IjYwMCIgZmlsbD0iI0VEMjkzOSIvPgogIDxyZWN0IHdpZHRoPSI2MDAiIGhlaWdodD0iNjAwIiBmaWxsPSIjRkZGIi8+CiAgPHJlY3Qgd2lkdGg9IjMwMCIgaGVpZ2h0PSI2MDAiIGZpbGw9IiMwMDIzOTUiLz4KPC9zdmc+)

[![Minecraft](https://img.shields.io/badge/Minecraft-1.21-brightgreen.svg)](https://www.minecraft.net/)
[![Fabric](https://img.shields.io/badge/Fabric-0.18.4-orange.svg)](https://fabricmc.net/)
[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](./LICENSE)

[English](README.md) | **Français**

 </div>

---

## 📖 Description

**GuildChat Shortener** est un mod client Fabric qui transforme les longs messages du bridge Discord dans votre guild Hypixel en messages courts et élégants. Fini les messages encombrants - personnalisez l'affichage du bridge avec des couleurs et des alias à votre goût !

### ✨ Fonctionnalités principales

- 🤖 **Détection automatique** - Le mod détecte automatiquement les messages du bridge Discord
- 🌍 **Support multilingue** - Support complet de l'anglais et du français
- 🎨 **Personnalisation des couleurs** - Changez les couleurs de l'alias du bridge et des pseudos
- 🏷️ **Alias personnalisable** - Remplacez le nom du bot par un alias court (ex: "Discord", "Bridge", "DC")
- ⚙️ **Configuration flexible** - Commandes intuitives pour ajuster la configuration en jeu
- 🔄 **Détection intelligente** - Reconnaît automatiquement les messages du bridge Discord
- 💾 **Sauvegarde automatique** - Votre configuration est persistante entre les sessions
- 🌐 **Mode global** - Activez le formatage pour tous les messages de guilde

---

## 📥 Installation

1. **Prérequis** :
   - Minecraft **1.21** ou supérieur
   - [Fabric Loader](https://fabricmc.net/use/) **0.18.0** ou supérieur
   - [Fabric API](https://modrinth.com/mod/fabric-api)
   - Java **21** ou supérieur

2. **Installation** :
   - Téléchargez le fichier `.jar` depuis la page [Releases](https://github.com/Tjiba/GuildChatShortener/releases)
   - Placez le fichier dans votre dossier `mods/`
   - Lancez Minecraft avec le profil Fabric

---

## 🎯 Utilisation

### Démarrage automatique

**Le mod détecte automatiquement les messages du bridge !** Vous n'avez rien à configurer - le formatage s'active tout seul dès qu'un message du bridge Discord est détecté dans votre guild.

### Configuration manuelle (optionnelle)

Si la détection automatique ne fonctionne pas, vous pouvez configurer manuellement le nom du bot Discord :

```
/bridgesetup <nomMC> <alias>
```

**Exemple** :
```
/bridgesetup BotDiscord DC
```

### Commandes principales

| Commande | Description | Exemple |
|----------|-------------|---------|
| `/bridge status` | Affiche la configuration actuelle | - |
| `/bridge reset` | Réinitialise toute la configuration | - |
| `/bridge help` | Affiche l'aide rapide | - |

### 🏷️ Gestion de l'alias

| Commande | Description | Exemple |
|----------|-------------|---------|
| `/bridgename <alias>` | Change l'alias du bridge | `/bridgename Discord` |
| `/bridgename reset` | Remet l'alias par défaut ("Bridge") | - |

### 🎨 Personnalisation des couleurs

| Commande | Alias | Description |
|----------|-------|-------------|
| `/bridgecolor <couleur>` | `/bc <couleur>` | Change la couleur de l'alias |
| `/bridgecolor reset` | `/bc reset` | Remet la couleur cyan par défaut |
| `/bridgeplayercolor <couleur>` | `/bpc <couleur>` | Change la couleur du pseudo joueur |
| `/bridgeplayercolor reset` | `/bpc reset` | Remet la couleur cyan foncé par défaut |

**Exemples** :
```
/bc jaune          # Alias du bridge en jaune
/bpc vert          # Pseudo joueur en vert
/bridgecolor &e    # Alias en jaune (code couleur)
```

### 🌐 Mode global

Active ou désactive le formatage pour **tous** les messages de la guilde :

```
/bridgeactivateall       # Active le mode global
/bridgeactivateall off   # Désactive le mode global
```

### 🌍 Paramètres de langue

Changez la langue du mod (tous les messages et commandes) :

```
/bridgelanguage <langue>
```

**Langues disponibles** :
- `English` ou `en` - Interface anglaise (par défaut)
- `Français` ou `fr` - Interface française

**Exemples** :
```
/bridgelanguage English    # Switch to English
/bridgelanguage fr         # Passer au français
```

---

## 🎨 Couleurs disponibles

Le paramètre `<couleur>` accepte les noms en français/anglais ou les codes Minecraft (`&` ou `§`) :

| Nom | Code | Aperçu |
|-----|------|--------|
| `noir` / `black` | `&0` | Noir |
| `bleu fonce` / `dark_blue` | `&1` | Bleu foncé |
| `vert fonce` / `dark_green` | `&2` | Vert foncé |
| `cyan fonce` / `dark_aqua` | `&3` | Cyan foncé ⭐ (défaut joueur) |
| `rouge fonce` / `dark_red` | `&4` | Rouge foncé |
| `violet fonce` / `dark_purple` | `&5` | Violet foncé |
| `or` / `gold` | `&6` | Or |
| `gris` / `gray` | `&7` | Gris |
| `gris fonce` / `dark_gray` | `&8` | Gris foncé |
| `bleu` / `blue` | `&9` | Bleu |
| `vert` / `green` | `&a` | Vert |
| `cyan` / `aqua` | `&b` | Cyan ⭐ (défaut bridge) |
| `rouge` / `red` | `&c` | Rouge |
| `rose clair` / `light_purple` | `&d` | Rose clair |
| `jaune` / `yellow` | `&e` | Jaune |
| `blanc` / `white` | `&f` | Blanc |

---

## 🛠️ Développement

### Build le projet

```cmd
gradlew.bat build
```

Le fichier JAR compilé se trouve dans `build/libs/GuildChatShortener-1.0.0.jar`.

### Structure du projet

```
src/main/
├── java/com/guildchat/formatter/
│   ├── GuildChatMod.java          # Point d'entrée principal
│   ├── BridgeConfig.java          # Gestion de la configuration
│   └── mixin/
│       └── ChatHudMixin.java      # Injection pour formater les messages
└── resources/
    ├── fabric.mod.json            # Métadonnées du mod
    └── guildchat-formatter.mixins.json
```

---

## 📝 Changelog

Voir [CHANGELOG.md](CHANGELOG.md) pour l'historique des versions.

---

## 🤝 Contribution

Les contributions sont les bienvenues ! N'hésitez pas à :
- 🐛 Signaler des bugs
- 💡 Proposer de nouvelles fonctionnalités
- 🔧 Soumettre des pull requests

---

## 📜 Licence

Ce projet est sous licence **MIT**. Voir le fichier [LICENSE](./LICENSE) pour plus de détails.

---

## 👤 Auteur

Développé avec ❤️ par **[Tjiba](https://github.com/Tjiba)** (MeteoFrance en jeu)

---

<div style="text-align: center;">

### ⭐ Si ce mod vous est utile, n'hésitez pas à lui donner une étoile !

[Télécharger](https://github.com/Tjiba/GuildChatShortener/releases) • [Signaler un bug](https://github.com/Tjiba/GuildChatShortener/issues) • [Demander une fonctionnalité](https://github.com/Tjiba/GuildChatShortener/issues)

 </div>
