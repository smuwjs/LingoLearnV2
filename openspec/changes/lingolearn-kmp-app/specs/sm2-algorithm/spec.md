## ADDED Requirements

### Requirement: SM-2 algorithm implementation
The system SHALL implement the SuperMemo 2 (SM-2) spaced repetition algorithm to calculate optimal review intervals for vocabulary flashcards.

#### Scenario: Calculate next review
- **WHEN** user rates a flashcard with quality 0-5
- **THEN** the system SHALL calculate new ease factor using formula: `EF' = EF + (0.1 - (5-q) * (0.08 + (5-q) * 0.02))`
- **AND** calculate new interval: if q < 3 then interval = 1, else if repetitions = 1 then interval = 1, else if repetitions = 2 then interval = 6, else interval = round(previousInterval * EF)

### Requirement: Quality rating mapping
The system SHALL map user swipe gestures to SM-2 quality ratings: "不认识" (left swipe) = 0-2, "认识" (right swipe) = 3-5.

#### Scenario: Quality rating from swipe
- **WHEN** user swipes left marking word as "不认识"
- **THEN** system SHALL use quality rating of 1 (complete blackout)
- **AND WHEN** user swipes right marking word as "认识"
- **THEN** system SHALL use quality rating of 4 (correct with hesitation)

### Requirement: Review scheduling
The system SHALL schedule words for review based on calculated intervals and make only due words available in learning mode.

#### Scenario: Only due words presented
- **WHEN** user starts a learning session
- **THEN** system SHALL only present words where `nextReviewDate <= today`
