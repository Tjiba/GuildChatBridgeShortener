# Release process

## Build the jar

```cmd
gradlew.bat build
```

Jar output:
- `build/libs/GuildChatShortener-1.0.0.jar`
- Optionnel: `build/libs/GuildChatShortener-1.0.0-sources.jar`

## GitHub release

1. Cree un tag `v1.0.0` (ou utilise un tag existant).
2. Ouvre GitHub -> Releases -> Draft a new release.
3. Selectionne le tag `v1.0.0`.
4. Attache le jar depuis `build/libs/`.
5. Colle les notes depuis `CHANGELOG.md` et publie.
