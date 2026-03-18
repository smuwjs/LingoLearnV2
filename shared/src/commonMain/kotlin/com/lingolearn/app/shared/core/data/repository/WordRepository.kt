package com.lingolearn.app.shared.core.data.repository

import com.lingolearn.app.shared.core.data.model.WordModel
import com.lingolearn.app.shared.core.data.model.toDomain
import com.lingolearn.app.shared.core.data.seed.Cet4Vocabulary
import com.lingolearn.app.shared.core.domain.model.Word
import com.lingolearn.app.shared.core.domain.model.WordCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class WordRepository {
    private val words = MutableStateFlow<List<Word>>(emptyList())

    init {
        // Seed initial vocabulary data
        words.value = Cet4Vocabulary.getWords()
    }

    fun getAllWords(): Flow<List<Word>> = words

    fun getWordsByCategory(category: WordCategory): Flow<List<Word>> {
        return words.map { list -> list.filter { it.category == category } }
    }

    fun getWordById(id: String): Word? {
        return words.value.find { it.id == id }
    }

    suspend fun insertWord(word: Word) {
        words.value = words.value + word
    }

    suspend fun insertWords(newWords: List<Word>) {
        words.value = words.value + newWords
    }

    fun getWordCount(): Long = words.value.size.toLong()
}
