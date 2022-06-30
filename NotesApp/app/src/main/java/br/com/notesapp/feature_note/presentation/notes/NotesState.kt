package br.com.notesapp.feature_note.presentation.notes

import br.com.notesapp.feature_note.domain.model.Note
import br.com.notesapp.feature_note.presentation.util.NoteOrder
import br.com.notesapp.feature_note.presentation.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)