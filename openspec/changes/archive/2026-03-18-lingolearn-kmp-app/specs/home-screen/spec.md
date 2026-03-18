## ADDED Requirements

### Requirement: Daily progress ring
The system SHALL display a circular progress indicator showing completed words vs daily goal on the home screen.

#### Scenario: Progress ring updates
- **WHEN** user completes a word
- **THEN** the progress ring SHALL animate to reflect new completion count
- **AND** display percentage as `completedWords / dailyGoal * 100`

### Requirement: Streak counter
The system SHALL track consecutive days of learning and display a flame icon with streak count.

#### Scenario: Streak maintained
- **WHEN** user completes at least one word on consecutive days
- **THEN** the streak counter SHALL increment by 1 each day
- **AND WHEN** user misses a day (no words completed)
- **THEN** the streak SHALL reset to 0

### Requirement: Due review badge
The system SHALL display a badge showing number of words awaiting review.

#### Scenario: Review badge visibility
- **WHEN** there are words with `nextReviewDate <= today`
- **THEN** a badge with the count SHALL appear on the home screen
- **AND WHEN** there are no due reviews
- **THEN** the badge SHALL be hidden

### Requirement: Quick action buttons
The system SHALL provide three buttons: "开始学习" (Start Learning), "快速复习" (Quick Review), "随机测试" (Random Test).

#### Scenario: Button navigation
- **WHEN** user taps "开始学习"
- **THEN** navigate to LearningScreen with new words
- **AND WHEN** user taps "快速复习"
- **THEN** navigate to LearningScreen filtered to due review words
- **AND WHEN** user taps "随机测试"
- **THEN** navigate to PracticeScreen with random words
