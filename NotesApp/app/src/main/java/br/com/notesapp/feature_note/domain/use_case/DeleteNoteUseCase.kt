package br.com.notesapp.feature_note.domain.use_case

import br.com.notesapp.feature_note.domain.model.Note
import br.com.notesapp.feature_note.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note = note)
    }
}