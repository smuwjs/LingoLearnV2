## ADDED Requirements

### Requirement: Daily goal setting
The system SHALL allow user to set daily learning goal between 10 and 100 words.

#### Scenario: Goal adjustment
- **WHEN** user adjusts goal slider
- **THEN** display updates in real-time showing selected number
- **AND** persist selection immediately

### Requirement: Reminder notifications
The system SHALL allow user to enable/disable daily reminder and set reminder time.

#### Scenario: Reminder toggle
- **WHEN** user enables reminder
- **THEN** show time picker
- **AND** schedule local notification at selected time daily
- **AND WHEN** user disables reminder
- **THEN** cancel scheduled notification

### Requirement: Sound and vibration toggles
The system SHALL allow user to toggle sound effects and haptic feedback independently.

#### Scenario: Toggle persistence
- **WHEN** user toggles sound or vibration setting
- **THEN** setting SHALL persist immediately
- **AND** apply to all future interactions

### Requirement: Auto-play TTS toggle
The system SHALL allow user to enable/disable automatic pronunciation playback when card flips.

#### Scenario: Auto-play setting
- **WHEN** auto-play is enabled
- **THEN** TTS SHALL play automatically after card flip
- **AND WHEN** disabled
- **THEN** user must tap speaker icon manually

### Requirement: Theme selection
The system SHALL allow user to select theme mode: System Default, Light, Dark.

#### Scenario: Theme change
- **WHEN** user selects theme option
- **THEN** app SHALL immediately apply corresponding theme
- **AND** persist selection

### Requirement: Progress reset
The system SHALL allow user to reset all learning progress after confirmation.

#### Scenario: Reset confirmation
- **WHEN** user taps "Reset Progress"
- **THEN** show confirmation dialog requiring explicit confirmation
- **AND WHEN** user confirms
- **THEN** clear all progress data but retain vocabulary
