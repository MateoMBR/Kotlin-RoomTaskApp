# 🎯 Room Task App

Une application Android simple de gestion de tâches utilisant **Room Database** et **Jetpack Compose**.

## 📋 Fonctionnalités

- ✅ Ajouter des tâches
- ✅ Afficher la liste des tâches en temps réel
- ✅ Supprimer des tâches

## 🛠️ Technologies

- **Room**: ORM local persistant
- **Jetpack Compose**: UI moderne
- **Kotlin Coroutines**: Programmation asynchrone
- **Flow**: Observation réactive des données

## 🚀 Mise en route

```bash
chmod +x gradlew
./gradlew build
./gradlew installDebug
```

## 📁 Structure du projet

```
app/src/main/
├── java/com/example/roomtask/
│   ├── TaskSchema.kt    # Entity, DAO, Database
│   └── MainActivity.kt   # UI Compose
└── AndroidManifest.xml
```

## 📚 Apprentissage

Ce projet implémente l'exemple du PDF "Room Kotlin" avec:
- Une entité `Task` avec auto-increment ID
- Un DAO `TaskDao` avec insert, delete, et Query
- Une database `AppDb` centralisée
- Une UI réactive utilisant `collectAsState()`

## 🔗 Ressources

- [Room Documentation](https://developer.android.com/training/data-storage/room)
- [Jetpack Compose](https://developer.android.com/compose)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
