package com.lingolearn.app.shared.core.domain.model

data class Word(
    val id: String,
    val word: String,
    val phonetic: String,
    val meaning: String,
    val example: String,
    val audioUrl: String?,
    val category: WordCategory,
    val createdAt: Long
)

enum class WordCategory {
    CET4, CET6
}
