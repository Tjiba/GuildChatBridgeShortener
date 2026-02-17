# GuildChat Bridge Shortener

## Commandes en jeu

Ces commandes sont enregistrees cote client et permettent de configurer le bridge.

- `/bridge reset` : reinitialise la config (nom MC auto, alias "Bridge", couleurs par defaut).
- `/bridge status` : affiche la configuration actuelle (nom MC, alias, couleurs).
- `/bridge help` : affiche l aide rapide des commandes.
- `/bridgesetup <nomMC> <alias>` : definit le nom MC du bot et l alias affiche.
- `/bridgename reset` : remet l alias du bridge a "Bridge".
- `/bridgename <alias>` : change l alias du bridge.
- `/bridgecolor reset` : remet la couleur de l alias du bridge a cyan (&b).
- `/bridgecolor <couleur>` : change la couleur de l alias du bridge.
- `/bridgeplayercolor reset` : remet la couleur du pseudo joueur a cyan fonce (&3).
- `/bridgeplayercolor <couleur>` : change la couleur du pseudo joueur.
- `/bc ...` : alias court de `/bridgecolor ...`.
- `/bpc ...` : alias court de `/bridgeplayercolor ...`.
- `/bridgeactivateall [off]` : active le formatage pour tous les messages de guilde (ou le desactive avec `off`).

## Couleurs accepteees

Le parametre `<couleur>` accepte un nom FR/EN ou un code `&`/`§`.

- noir (&0), bleu fonce (&1), vert fonce (&2), cyan fonce (&3), rouge fonce (&4)
- violet fonce (&5), or (&6), gris (&7), gris fonce (&8), bleu (&9)
- vert (&a), cyan (&b), rouge (&c), rose clair (&d), jaune (&e), blanc (&f)

## Release GitHub

Build:

```cmd
./gradlew build
```

Le jar a publier se trouve dans `build/libs/` (ex: `GuildChatShortener-1.0.0.jar`).