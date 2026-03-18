## ADDED Requirements

### Requirement: Vocabulary data model
The system SHALL store vocabulary words with English spelling, phonetic transcription, Chinese meaning, example sentence, and audio pronunciation URL.

#### Scenario: Word model structure
- **WHEN** a vocabulary word is stored
- **THEN** it MUST contain: `id` (UUID), `word` (String), `phonetic` (String), `meaning` (String), `example` (String), `audioUrl` (String?), `category` (CET4|CET6), `createdAt` (timestamp)

### Requirement: Learning progress tracking
The system SHALL track each user's learning progress per word including repetition count, ease factor, interval, next review date, and mastery level.

#### Scenario: Progress per word
- **WHEN** a user learns a word
- **THEN** the system MUST record: `wordId` (UUID), `userId` (String), `repetitions` (Int), `easeFactor` (Float), `interval` (Int days), `nextReviewDate` (timestamp), `masteryLevel` (0-5), `lastReviewedAt` (timestamp?)

### Requirement: Local persistence
The system SHALL persist all data locally on device using Realm Kotlin SDK and automatically sync in-memory state with database.

#### Scenario: Data survives app restart
- **WHEN** app is closed and reopened
- **THEN** all previously saved vocabulary and progress data SHALL remain intact

### Requirement: User settings storage
The system SHALL store user preferences including daily goal, reminder settings, sound/vibration toggles, and theme mode.

#### Scenario: Settings persistence
- **WHEN** user changes a setting
- **THEN** the setting MUST persist across app restarts
