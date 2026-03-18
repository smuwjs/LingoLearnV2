# LingoLearn V2 - 背单词应用

## 项目概述

使用 Kotlin Multiplatform (KMP) 开发一款背单词应用，支持 Android 和 iOS 平台。

## 技术栈

- Kotlin Multiplatform (KMP) 1.9.22
- Compose Multiplatform 1.6.1
- Material Design 3
- SM-2 间隔重复算法

## 设计规范

- 主色调：蓝色 #0EA5E9
- 辅助色：青绿 #14B8A6
- 支持 Light/Dark Mode
- Material Design 3 风格

## 功能模块

### 1. 首页 (HomeScreen)
- 环形进度条显示今日学习进度（已学/目标）
- 火焰图标 + 连续打卡天数
- 待复习单词数量角标提醒
- 快捷按钮：开始学习、快速复习、随机测试

### 2. 单词学习 (LearningScreen)
- 卡片正面：英文单词 + 音标
- 卡片背面：中文释义 + 例句
- 点击卡片触发 3D 翻转动画
- 右滑标记"认识"，左滑标记"不认识"，上滑收藏
- 滑动时卡片倾斜 + 颜色渐变反馈
- 点击喇叭图标播放发音（系统 TTS）
- 每组学习结束显示本轮统计弹窗
- 基于 SM-2 算法自动安排复习计划

### 3. 练习测试 (PracticeScreen)
- 三种题型可选：选择题 / 填空题 / 听力题
- 选择题：显示单词，四选一选中文释义
- 填空题：显示中文，键盘输入英文单词
- 听力题：播放发音，四选一选正确单词
- 顶部倒计时进度条
- 答对绿色对勾动画，答错红色抖动动画
- 练习结束页：正确率、用时、错题列表

### 4. 学习进度 (ProgressScreen)
- 折线图展示近 7 天/30 天学习单词数
- 日历热力图显示学习频率
- 饼图显示词汇掌握度分布
- 成就徽章墙

### 5. 设置 (SettingsScreen)
- 每日学习目标（10-100 个）
- 学习提醒开关 + 时间选择器
- 音效开关 / 震动反馈开关
- 自动播放发音开关
- 外观：跟随系统 / 浅色 / 深色
- 重置学习进度

## 数据要求

- 预置 500+ 单词数据（CET4/CET6 分类）
- 本地数据持久化
- 学习进度跟踪

## 项目结构

```
LingoLearnV2/
├── androidApp/          # Android 应用入口
├── iosApp/              # iOS 应用入口
├── shared/              # 共享模块
│   └── src/
│       ├── commonMain/  # 共享代码
│       └── androidMain/ # Android 特定实现
└── build.gradle.kts
```