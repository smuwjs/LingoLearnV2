## Context

LingoLearn V2 is a new Kotlin Multiplatform (KMP) flashcard application for English vocabulary learning. It targets both Android and iOS from a shared codebase using Compose Multiplatform. The app implements the SM-2 spaced repetition algorithm to optimize learning retention and automate review scheduling.

**Current State**: This is a greenfield project. The `REQUIREMENTS.md` defines the feature set but no code exists yet.

**Constraints**:
- Kotlin Multiplatform 1.9.22 with Compose Multiplatform 1.6.1
- Material Design 3 theming with light/dark mode support
- Primary color: Blue #0EA5E9, Secondary: Teal #14B8A6
- Local-only data (no backend), pre-loaded with 500+ CET4/CET6 vocabulary

## Goals / Non-Goals

**Goals:**
- Deliver a working KMP shell project with Android and iOS entry points
- Implement all five screens with Compose Multiplatform UI
- Integrate SM-2 algorithm for spaced repetition scheduling
- Support swipe gestures and 3D flip animations for flashcard interactions
- Provide practice modes with choice, fill-in-blank, and listening question types
- Visualize learning progress with charts and achievement badges
- Persist all user data locally

**Non-Goals:**
- Backend API or cloud sync
- User authentication
- Social features or sharing
- Custom vocabulary import (v1 will use pre-loaded words only)

## Decisions

### 1. Architecture: Feature-Based MVVM with Clean Architecture Layers

**Decision**: Organize code by feature (home, learning, practice, progress, settings) with shared core modules for data and domain logic.

**Structure**:
```
shared/src/commonMain/
├── core/
│   ├── data/          # Repositories, data sources, models
│   ├── domain/        # Use cases, domain models
│   └── algorithm/     # SM-2 implementation
├── feature/
│   ├── home/
│   ├── learning/
│   ├── practice/
│   ├── progress/
│   └── settings/
└── ui/
    ├── theme/
    └── components/
```

**Rationale**: Feature isolation enables parallel development and easier testing. Shared core prevents duplication across features.

### 2. State Management: Compose State + ViewModel

**Decision**: Use Compose `StateFlow` in ViewModels, with `remember` and `derivedStateOf` for UI-specific state.

**Rationale**: Standard Compose pattern with good lifecycle handling. ViewModels survive configuration changes on Android and are simple to implement in KMP.

### 3. Local Persistence: Realm Kotlin SDK

**Decision**: Use Realm Kotlin for local database.

**Rationale**: Realm provides reactive queries that integrate well with Flow/StateFlow. Cross-platform support (JVM, Native, JS). Better schema evolution than SQLite migrations.

**Alternatives considered**:
- SQLite with Room: More mature but requires Android-specific setup in KMP
- DataStore: Good for preferences but limited querying for flashcard scheduling
- Realm: Best cross-platform support for complex queries needed by SM-2

### 4. Navigation: Compose Navigation Multiplatform

**Decision**: Use `com.multiplatform.navigation:compose` or manual navigation with state hoisting.

**Rationale**: Jetbrains provides alpha navigation library for KMP. Simpler approach with sealed class destinations and back stack management.

### 5. Dependency Injection: Koin

**Decision**: Use Koin for dependency injection.

**Rationale**: Koin is fully Kotlin, has excellent KMP support, and simpler setup than Kodein or manual DI. Works well with Compose ViewModels.

### 6. Charts: Vico

**Decision**: Use Vico charting library for Compose.

**Rationale**: Vico is a Compose-native charting library with good customization. Supports line charts, bar charts, and pie charts needed for progress visualization.

## Risks / Trade-offs

**[Risk] KMP compilation time** → Mitigation: Use Gradle build caching, configure parallel compilation, and consider using K2 compiler for faster builds.

**[Risk] iOS UI differences** → Mitigation: Test on both platforms early. Use platform-specific modifiers where needed. Target 80% shared UI with minor adaptations.

**[Risk] SM-2 complexity for future changes** → Mitigation: Isolate algorithm in dedicated module with unit tests. Document algorithm clearly in specs.

**[Risk] Large vocabulary data in initial bundle** → Mitigation: Ship with subset (200 words), lazy-load rest. Consider downloadable word packs in v2.

**[Trade-off] Local-only vs cloud sync** → Current scope excludes cloud for simplicity, but data models should be designed to support future sync if needed.
