package br.com.notesapp.feature_note.domain.use_case

import br.com.notesapp.feature_note.domain.model.InvalidNoteException
import br.com.notesapp.feature_note.domain.model.Note
import br.com.notesapp.feature_note.domain.repository.NoteRepository

class AddNoteUseCase(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw  InvalidNoteException("O titulo da nota não pode ser vazio.")
        }
        if (note.content.isBlank()) {
            throw  InvalidNoteException("O conteudo da nota não pode ser vazio.")
        }
        repository.insertNote(note = note)
    }
}