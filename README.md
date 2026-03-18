# LingoLearn V2

一款基于 Kotlin Multiplatform 开发的背单词应用，支持 Android 和 iOS 平台。

## 功能特点

- **首页** - 环形进度条显示学习进度，火焰图标展示连续打卡天数
- **单词学习** - 卡片式学习界面，3D翻转动画，基于 SM-2 间隔重复算法
- **练习测试** - 选择题、填空题、听力题三种题型
- **学习进度** - 图表展示学习数据，日历热力图，成就系统
- **设置** - 每日学习目标、学习提醒、主题模式（浅色/深色/跟随系统）

## 技术栈

| 技术 | 版本 |
|------|------|
| Kotlin Multiplatform | 1.9.22 |
| Compose Multiplatform | 1.6.1 |
| Material Design 3 | - |
| Realm Database | - |
| SM-2 间隔重复算法 | - |

## 项目结构

```
LingoLearnV2/
├── androidApp/          # Android 应用入口
├── iosApp/              # iOS 应用入口
├── shared/              # 共享模块
│   └── src/
│       ├── commonMain/  # 共享代码
│       │   ├── core/    # 核心算法和数据层
│       │   ├── feature/ # 业务功能模块
│       │   └── ui/      # UI 组件和主题
│       └── commonTest/ # 单元测试
└── build.gradle.kts
```

## 快速开始

### 环境要求

- JDK 17+
- Android Studio Arctic Fox+
- Xcode 15+ (for iOS)

### 运行 Android

```bash
./gradlew :androidApp:installDebug
```

### 运行 iOS

```bash
cd iosApp
xcodebuild -scheme iosApp -configuration Debug -destination 'platform=iOS Simulator'
```

## 数据

- 预置 CET-4 词汇数据
- 本地 Realm 数据库持久化

## 许可证

MIT License
