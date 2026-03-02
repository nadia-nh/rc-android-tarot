# AGENTS.md - Arcana Flux Development Guide

## Project Overview

This is an Android Jetpack Compose application for a Tarot card reading app. The project uses:
- **Language**: Kotlin 2.0
- **UI Framework**: Jetpack Compose with Material 3
- **Database**: Room
- **Architecture**: MVVM with StateFlow

## Build Commands

### Gradle Wrapper
The project uses Gradle via the wrapper. All commands should use `./gradlew` (Linux/Mac) or `gradlew.bat` (Windows).

### Build Debug APK
```bash
./gradlew assembleDebug
```

### Build Release APK
```bash
./gradlew assembleRelease
```

### Run Tests
Run all unit tests:
```bash
./gradlew test
```

Run a single unit test:
```bash
./gradlew test --tests "com.example.simpletarot.ExampleUnitTest"
./gradlew test --tests "com.example.simpletarot.TarotDeckTest"  # if it exists
```

Run instrumented tests (requires device/emulator):
```bash
./gradlew connectedAndroidTest
```

Run a single instrumented test:
```bash
./gradlew connectedAndroidTest --tests "com.example.simpletarot.ExampleInstrumentedTest"
```

### Lint
Run Android lint analysis:
```bash
./gradlew lint
```

### Clean Build
```bash
./gradlew clean
```

### Build with Verbose Output
```bash
./gradlew assembleDebug --info
```

## Code Style Guidelines

### Language Version
- Kotlin 2.0 (defined in `app/build.gradle.kts`)
- JVM target: 11

### Project Structure
```
app/src/main/java/com/example/simpletarot/
├── MainActivity.kt
├── TarotMain.kt
├── TarotViewModel.kt
├── TarotViewModelFactory.kt
├── CardDisplay.kt
├── CardImage.kt
├── MenuScreen.kt
├── ResultScreen.kt
├── HistoryScreen.kt
├── data/
│   ├── TarotCard.kt
│   ├── TarotDeck.kt
│   └── PreviewConstants.kt
├── database/
│   ├── TarotDatabase.kt
│   ├── TarotDao.kt
│   ├── TarotRepository.kt
│   ├── ReadingEntity.kt
│   └── DrawnCardEntity.kt
└── ui/theme/
    ├── Theme.kt
    ├── Color.kt
    ├── Type.kt
    ├── Spacing.kt
    └── Shapes.kt
```

### Naming Conventions

**Classes and Files**:
- Use PascalCase for class names (e.g., `TarotViewModel`, `TarotCard`)
- File names match class names (e.g., `TarotViewModel.kt`)

**Packages**:
- All lowercase, no underscores (e.g., `com.example.simpletarot.database`)

**Functions and Variables**:
- Use camelCase (e.g., `drawCards`, `currentSpread`)
- Private members use underscore prefix for backing fields: `_currentSpread`

**Enums**:
- Use PascalCase with descriptive names (e.g., `Suit.MajorArcana`, `Rank.Ace`)

### Import Organization

Imports follow this order (as seen in source files):
1. Kotlin standard library
2. Android/Compose imports
3. Project internal imports (grouped by package)

Example from `TarotViewModel.kt`:
```kotlin
import com.example.simpletarot.database.DrawnCardEntity
import com.example.simpletarot.database.ReadingEntity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletarot.data.DrawnCard
import com.example.simpletarot.data.TarotDeck
import com.example.simpletarot.database.ReadingWithCards
import com.example.simpletarot.database.TarotRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
```

### Data Classes

Use Kotlin data classes for simple holders:
```kotlin
data class TarotCard(
    val name: String,
    val uprightMeaning: String,
    val reversedMeaning: String,
    val suit: Suit = Suit.MajorArcana,
    val rank: Rank? = null,
)
```

### Compose Patterns

**Screen Components**:
- Stateless composables where possible
- ViewModel provides StateFlow for UI state
- Use `remember` and `mutableStateOf` for local UI state

**Theme**:
- Use `SimpleTarotTheme` composable wrapper
- Access theme values via `MaterialTheme.colors`, `MaterialTheme.typography`
- Custom spacing via `LocalSpacing.current`

### Database (Room)

**Entities**:
- Use `@Entity` annotation
- Primary key with auto-generation: `@PrimaryKey(autoGenerate = true)`
- Foreign keys for relationships

**DAO**:
- Use `@Dao` annotation
- Coroutine-based queries returning `Flow` or `suspend` functions

**Repository**:
- Single source of truth pattern
- Provides Flow-based data access

### Error Handling

- Use Kotlin's built-in null safety (`?.`, `?:`, `?.let`)
- Avoid `!!` operator unless absolutely certain
- Use `emptyList()` as default for nullable collections

### State Management

- ViewModels hold UI state in `MutableStateFlow`
- Expose immutable `StateFlow` to Composables
- Use `WhileSubscribed(5000)` for StateFlow sharing

### Testing

- Unit tests go in `app/src/test/java/com/example/simpletarot/`
- Instrumented tests go in `app/src/androidTest/java/com/example/simpletarot/`
- Use JUnit 4 with AndroidJUnit4 runner for instrumented tests

### ProGuard/Release

- ProGuard rules file: `app/proguard-rules.pro`
- Release builds have `isMinifyEnabled = false`

### Additional Notes

- Package name: `com.example.simpletarot`
- Min SDK: 24
- Target/Compile SDK: 36
- No detekt or ktlint configured (uses default Kotlin style)
- Code style in gradle.properties: `official`
