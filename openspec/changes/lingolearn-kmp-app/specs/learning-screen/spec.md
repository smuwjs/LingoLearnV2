## ADDED Requirements

### Requirement: Flashcard display
The system SHALL display vocabulary flashcards with word on front and meaning on back.

#### Scenario: Card front shown
- **WHEN** a flashcard is presented
- **THEN** the front SHALL show: English word (large), phonetic transcription (small)
- **AND WHEN** user taps the card
- **THEN** the card SHALL flip with 3D rotation animation revealing the back

### Requirement: Card back content
The system SHALL display on card back: Chinese meaning, example sentence, and speaker icon for TTS.

#### Scenario: Card back revealed
- **WHEN** card is flipped
- **THEN** display: Chinese meaning (large), example sentence (medium), speaker icon button (bottom)

### Requirement: Swipe gestures for learning
The system SHALL support swipe gestures: right = "认识" (know), left = "不认识" (don't know), up = favorite.

#### Scenario: Right swipe recognition
- **WHEN** user swipes right on a card
- **THEN** card SHALL tilt right with green tint fading in
- **AND** mark word as "known" with SM-2 quality 4
- **AND** slide card off screen right

#### Scenario: Left swipe recognition
- **WHEN** user swipes left on a card
- **THEN** card SHALL tilt left with red tint fading in
- **AND** mark word as "unknown" with SM-2 quality 1
- **AND** slide card off screen left

### Requirement: TTS pronunciation
The system SHALL play pronunciation audio when user taps speaker icon using system TTS.

#### Scenario: TTS playback
- **WHEN** user taps speaker icon
- **THEN** system SHALL call platform TTS to pronounce the English word
