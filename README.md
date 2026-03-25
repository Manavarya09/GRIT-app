# GRIT

A neo-brutalist productivity and accountability Android app built with Kotlin and Jetpack Compose.

## Design Philosophy

GRIT embraces neo-brutalism as a core design principle:
- High-contrast colors: black, white, red, yellow
- Thick black borders (4-8dp) on all components
- Bold, monospace typography
- Raw, aggressive, intentionally unstyled UI
- Large, rectangular, visually loud buttons

The app rejects polished, glassmorphic design in favor of bold, harsh aesthetics that feel rebellious and uncomfortable.

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose (100% Compose, no XML)
- **Architecture**: MVVM with Clean Architecture
- **Database**: Room for local persistence
- **State Management**: ViewModel + StateFlow
- **Navigation**: Jetpack Navigation Compose

## Project Structure

```
app/src/main/java/com/grit/
├── domain/model/          # Domain models
│   ├── FocusSession.kt
│   ├── DistractionEvent.kt
│   ├── Stats.kt
│   └── SocialActivity.kt
├── data/
│   ├── local/
│   │   ├── GritDatabase.kt    # Room database
│   │   ├── dao/              # Data Access Objects
│   │   └── entity/           # Database entities
│   └── repository/           # Repository implementations
├── ui/
│   ├── Navigation.kt         # Navigation setup
│   ├── theme/               # Neo-brutalist theme
│   ├── components/         # Reusable composables
│   └── screens/           # App screens
├── GritApplication.kt
└── MainActivity.kt
```

## Features

### Focus Mode
- Full-screen timer with selected task
- Blocks navigation while active
- Exit requires explicit confirmation
- Displays aggressive messaging ("DO NOT QUIT")
- Records failures when user exits early

### Distraction Tracking
- Logs when user leaves focus mode prematurely
- Stores distraction events in Room database
- Tracks duration before each distraction

### Stats Dashboard
- Total focus time (all-time)
- Number of completed sessions
- Daily focus time
- Daily distraction count
- Visual performance bar

### Failure Log
- List of all distraction events
- Each entry shows as a "record of failure"
- Timestamp and duration before failure

### Community Feed
- Mocked social activity showing user actions
- Displays completed sessions and failures
- Simulates community engagement

## Building

### Prerequisites
- Android SDK 34
- JDK 17+
- Gradle 8.4+

### Build Commands

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Clean build
./gradlew clean assembleDebug
```

### Output APK

Debug APK location: `app/build/outputs/apk/debug/app-debug.apk`

## Dependencies

- Jetpack Compose BOM 2023.10.01
- Navigation Compose 2.7.5
- Room 2.6.1
- Lifecycle ViewModel Compose 2.6.2
- Kotlin Coroutines 1.7.3

## License

MIT License
