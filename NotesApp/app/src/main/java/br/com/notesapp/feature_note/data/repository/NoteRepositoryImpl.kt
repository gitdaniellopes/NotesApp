package br.com.notesapp.feature_note.data.repository

import br.com.notesapp.feature_note.data.data_source.NoteDao
import br.com.notesapp.feature_note.domain.model.Note
import br.com.notesapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl constructor(
    private val noteDao: NoteDao
) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> = noteDao.getNotes()

    override suspend fun getNoteById(id: Int): Note? = noteDao.getNoteById(id = id)

    override suspend fun insertNote(note: Note) = noteDao.insertNote(note = note)

    override suspend fun deleteNote(note: Note) = noteDao.deleteNote(note = note)
}