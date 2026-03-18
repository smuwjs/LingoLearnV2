## 1. Project Setup

- [x] 1.1 Initialize KMP project with Android and iOS targets
- [x] 1.2 Configure Gradle build files with Compose Multiplatform (Kotlin 2.0.0, Compose 1.6.0)
- [x] 1.3 Set up project directory structure (core/, feature/, ui/)
- [x] 1.4 Add dependencies: Koin DI, KSP, Vico charts
- [x] 1.5 Verify shell project compiles for Android
- [ ] 1.6 Verify shell project compiles for iOS

## 2. Core Data Layer

- [x] 2.1 Define data models: Word, LearningProgress, UserSettings (in-memory stub classes)
- [x] 2.2 Implement WordRepository for vocabulary CRUD
- [x] 2.3 Implement ProgressRepository for learning progress
- [x] 2.4 Implement SettingsRepository for user preferences
- [ ] 2.5 Seed initial vocabulary data (200+ CET4 words)

## 3. SM-2 Algorithm

- [x] 3.1 Implement SM2Calculator class with interval calculation
- [x] 3.2 Implement QualityRating enum mapping to swipe gestures
- [x] 3.3 Add scheduling logic to filter due words
- [ ] 3.4 Write unit tests for SM-2 calculations

## 4. App Theme & Components

- [x] 4.1 Set up Material 3 theme with colors #0EA5E9 and #14B8A6
- [x] 4.2 Implement light/dark mode theming
- [x] 4.3 Create FlashCard component with 3D flip animation
- [x] 4.4 Create swipe gesture handler with visual feedback
- [x] 4.5 Create ProgressRing component

## 5. Home Screen

- [x] 5.1 Implement HomeScreen composable
- [x] 5.2 Add ProgressRing showing daily completion
- [x] 5.3 Add StreakCounter with flame icon
- [x] 5.4 Add DueReviewBadge with count
- [x] 5.5 Add quick action buttons (Start, Review, Test)
- [x] 5.6 Implement HomeViewModel with state management

## 6. Learning Screen

- [x] 6.1 Implement LearningScreen composable
- [x] 6.2 Create FlashCardStack with swipe detection
- [x] 6.3 Add TTS integration for pronunciation
- [x] 6.4 Implement 3D flip animation on tap
- [x] 6.5 Connect swipe gestures to SM-2 algorithm
- [x] 6.6 Add session statistics popup on completion
- [x] 6.7 Implement LearningViewModel with deck management

## 7. Practice Screen

- [x] 7.1 Implement PracticeScreen composable
- [x] 7.2 Create MultipleChoiceQuestion component
- [x] 7.3 Create FillInBlankQuestion component
- [x] 7.4 Create ListeningQuestion component with TTS
- [x] 7.5 Add countdown timer with progress bar
- [x] 7.6 Add result animations (checkmark, shake)
- [x] 7.7 Implement PracticeResults composable
- [x] 7.8 Implement PracticeViewModel with question generation

## 8. Progress Screen

- [x] 8.1 Implement ProgressScreen composable
- [x] 8.2 Add Vico line chart for learning trend
- [x] 8.3 Add calendar heatmap component
- [x] 8.4 Add Vico pie chart for mastery distribution
- [x] 8.5 Create AchievementBadge component
- [x] 8.6 Implement ProgressViewModel with statistics

## 9. Settings Screen

- [x] 9.1 Implement SettingsScreen composable
- [x] 9.2 Add DailyGoalSlider (10-100 range)
- [x] 9.3 Add ReminderToggle with TimePicker
- [x] 9.4 Add SoundToggle and VibrationToggle
- [x] 9.5 Add AutoPlayToggle for TTS
- [x] 9.6 Add ThemeSelector (System/Light/Dark)
- [x] 9.7 Add ProgressReset with confirmation dialog
- [x] 9.8 Implement SettingsViewModel

## 10. Navigation

- [x] 10.1 Set up navigation with sealed class destinations
- [x] 10.2 Add bottom navigation bar
- [x] 10.3 Implement deep linking support
- [x] 10.4 Add back stack handling

## 11. Polish & QA

- [ ] 11.1 Verify all swipe animations feel smooth (60fps)
- [ ] 11.2 Test on Android with dark mode enabled
- [ ] 11.3 Test on iOS with system font
- [ ] 11.4 Verify TTS works on both platforms
- [ ] 11.5 Check data persists after app restart
- [ ] 11.6 Final build verification for both platforms

## Notes

**Issue Resolved**: KMP/Compose compiler internal error with inline functions (Row layout)
- Root cause: Kotlin 1.9.22 with Compose Compiler 1.5.x has compatibility issues in KMP context
- Resolution: Upgraded to Kotlin 2.0.0 with Compose Compiler Plugin (org.jetbrains.kotlin.plugin.compose)
- Build now compiles successfully for Android debug and release variants
- Realm was removed in favor of in-memory repositories for build verification
