## ADDED Requirements

### Requirement: Learning trend chart
The system SHALL display a line chart showing words learned per day over last 7 or 30 days.

#### Scenario: Line chart data
- **WHEN** user views progress screen
- **THEN** display line chart with X-axis as dates, Y-axis as word count
- **AND** toggle between 7-day and 30-day views

### Requirement: Calendar heatmap
The system SHALL display a calendar heatmap showing learning activity intensity per day.

#### Scenario: Heatmap visualization
- **WHEN** user views progress screen
- **THEN** display calendar grid with color intensity based on words learned that day
- **AND** green intensity scale: white (0) to dark green (max)

### Requirement: Mastery pie chart
The system SHALL display a pie chart showing distribution of word mastery levels.

#### Scenario: Pie chart segments
- **WHEN** user views progress screen
- **THEN** display pie chart with segments: New (level 0), Learning (1-2), Familiar (3-4), Mastered (5)
- **AND** show percentage and count for each segment

### Requirement: Achievement badges
The system SHALL display achievement badges for milestones: first word, 7-day streak, 30-day streak, 100 words learned, 500 words learned.

#### Scenario: Badge display
- **WHEN** user earns an achievement
- **THEN** badge SHALL appear in badge wall with earned date
- **AND** locked badges SHALL show as greyed out
