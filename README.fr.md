<div style="text-align:center">

# 🎮 GuildChat Shortener

**Mod Fabric pour raccourcir et personnaliser les messages du bridge Discord et des messages normaux dans les guildes Hypixel**

![France](https://img.shields.io/badge/Made%20in-France-blue?style=flat&logo=data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iOTAwIiBoZWlnaHQ9IjYwMCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KICA8cmVjdCB3aWR0aD0iOTAwIiBoZWlnaHQ9IjYwMCIgZmlsbD0iI0VEMjkzOSIvPgogIDxyZWN0IHdpZHRoPSI2MDAiIGhlaWdodD0iNjAwIiBmaWxsPSIjRkZGIi8+CiAgPHJlY3Qgd2lkdGg9IjMwMCIgaGVpZ2h0PSI2MDAiIGZpbGw9IiMwMDIzOTUiLz4KPC9zdmc+) **Fièrement créé en France** ![France](https://img.shields.io/badge/Made%20in-France-blue?style=flat&logo=data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iOTAwIiBoZWlnaHQ9IjYwMCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KICA8cmVjdCB3aWR0aD0iOTAwIiBoZWlnaHQ9IjYwMCIgZmlsbD0iI0VEMjkzOSIvPgogIDxyZWN0IHdpZHRoPSI2MDAiIGhlaWdodD0iNjAwIiBmaWxsPSIjRkZGIi8+CiAgPHJlY3Qgd2lkdGg9IjMwMCIgaGVpZ2h0PSI2MDAiIGZpbGw9IiMwMDIzOTUiLz4KPC9zdmc+)

[![Minecraft](https://img.shields.io/badge/Minecraft-1.21-brightgreen.svg)](https://www.minecraft.net/)
[![Fabric](https://img.shields.io/badge/Fabric-0.18.4-orange.svg)](https://fabricmc.net/)
[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](./LICENSE)

[English](README.md) | **Français**

<img width="265" height="61" alt="image" src="https://github.com/user-attachments/assets/6fdc0006-c53c-489d-a353-bf7e82031430" />

</div>

---

## 📖 Description

**GuildChat Shortener** est un mod client Fabric qui transforme les longs messages du bridge Discord et les messages normaux de guilde dans votre guild Hypixel en messages courts et élégants. Fini les messages encombrants - personnalisez l'affichage avec des couleurs et des alias à votre goût !

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

**Prérequis** :
- Minecraft 1.21+
- Fabric Loader 0.18.0+
- Fabric API (obligatoire)
- ModMenu (optionnel - pour la config graphique)
- Cloth Config (optionnel - pour la config graphique)

**Installation** :
1. Téléchargez depuis [Releases](https://github.com/Tjiba/GuildChatShortener/releases)
2. Placez dans votre dossier `mods/`
3. Lancez Minecraft avec le profil Fabric

---

## 🎯 Utilisation

**Le mod détecte automatiquement les messages du bridge !** La configuration est optionnelle mais peut être personnalisée avec des commandes ou ModMenu.

### Commandes essentielles

```
/bridge help              # Affiche l'aide
/bridgename <alias>       # Change l'alias (ex: /bridgename DC)
/bridgecolor <couleur>    # Change la couleur de l'alias (ex: /bridgecolor rouge)
/bridgeplayercolor <color> # Change la couleur du pseudo joueur
/bridgelanguage <lang>    # Change la langue (english/french)
```

**Exemples** :
```
/bridgename Bridge        # Met l'alias à "Bridge"
/bridgecolor jaune        # Rend l'alias jaune
/bridgelanguage french    # Passer au français
```

---

## 🎨 Couleurs disponibles

Utilisez les noms de couleurs dans les commandes : `noir`, `bleu fonce`, `vert fonce`, `cyan fonce`, `rouge fonce`, `violet fonce`, `or`, `gris`, `gris fonce`, `bleu`, `vert`, `cyan`, `rouge`, `rose clair`, `jaune`, `blanc`

**Exemples** : `/bc rouge`, `/bpc vert`, `/bridgecolor cyan`

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

<div style="text-align:center">

### ⭐ Si ce mod vous est utile, n'hésitez pas à lui donner une étoile !

[Télécharger](https://github.com/Tjiba/GuildChatShortener/releases) • [Signaler un bug](https://github.com/Tjiba/GuildChatShortener/issues) • [Demander une fonctionnalité](https://github.com/Tjiba/GuildChatShortener/issues)

</div>
