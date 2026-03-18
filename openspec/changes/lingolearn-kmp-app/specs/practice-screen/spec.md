## ADDED Requirements

### Requirement: Multiple choice questions
The system SHALL present a word with four Chinese meaning options, one correct.

#### Scenario: Multiple choice display
- **WHEN** user starts choice practice
- **THEN** display English word at top
- **AND** show 4 buttons with Chinese meanings (1 correct, 3 random wrong)
- **AND WHEN** user taps correct answer
- **THEN** show green checkmark animation and advance

### Requirement: Fill-in-blank questions
The system SHALL display Chinese meaning and accept typed English answer.

#### Scenario: Fill-in-blank input
- **WHEN** user starts fill-in-blank practice
- **THEN** display Chinese meaning and text input field
- **AND WHEN** user submits answer
- **THEN** compare ignoring case and show result animation

### Requirement: Listening questions
The system SHALL play pronunciation and ask user to identify the word from four options.

#### Scenario: Listening practice
- **WHEN** user starts listening practice
- **THEN** auto-play TTS pronunciation once
- **AND** show 4 word options (1 correct, 3 random)
- **AND WHEN** user selects correct answer
- **THEN** show green checkmark animation

### Requirement: Practice timer
The system SHALL display a countdown timer during practice sessions.

#### Scenario: Timer display
- **WHEN** practice session starts
- **THEN** display progress bar at top that depletes over session duration
- **AND WHEN** time expires
- **THEN** auto-submit current answer

### Requirement: Practice results
The system SHALL show results screen with accuracy percentage, time taken, and list of wrong answers.

#### Scenario: Results shown
- **WHEN** practice session completes
- **THEN** display: correct count / total, percentage, time elapsed
- **AND** list any incorrectly answered words with correct answers
