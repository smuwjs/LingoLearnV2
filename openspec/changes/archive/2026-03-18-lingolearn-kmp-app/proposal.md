## Why

Build a Kotlin Multiplatform (KMP) flashcard app for English vocabulary learning that targets both Android and iOS from a single codebase. The app uses the SM-2 spaced repetition algorithm to optimize learning retention and schedules reviews automatically.

## What Changes

- Create a new KMP project structure with Android and iOS targets
- Implement shared business logic and UI in Compose Multiplatform
- Build five core screens: Home, Learning, Practice, Progress, Settings
- Integrate SM-2 spaced repetition algorithm for flashcard scheduling
- Add TTS support for pronunciation playback
- Create data layer with local persistence for vocabulary and progress

## Capabilities

### New Capabilities

- `core-data`: Data models and local persistence for vocabulary, learning progress, and user settings
- `sm2-algorithm`: SM-2 spaced repetition algorithm implementation for flashcard scheduling
- `home-screen`: Home screen with daily progress ring, streak counter, and quick actions
- `learning-screen`: Flashcard learning with 3D flip animation and swipe gestures
- `practice-screen`: Practice modes with multiple question types (choice, fill-in-blank, listening)
- `progress-screen`: Statistics visualization with charts and achievement badges
- `settings-screen`: User preferences for learning goals, reminders, and app appearance

### Modified Capabilities

- None (new project)

## Impact

- New `androidApp/` and `iosApp/` directories for platform entry points
- New `shared/` module for cross-platform code
- Compose Multiplatform UI framework
- Local SQLite/Realm database for persistence
- System TTS integration for pronunciation
