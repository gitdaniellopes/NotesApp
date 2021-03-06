package br.com.notesapp.feature_note.presentation.notes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.notesapp.feature_note.domain.model.Note
import br.com.notesapp.feature_note.domain.use_case.NoteUseCases
import br.com.notesapp.feature_note.presentation.util.NoteOrder
import br.com.notesapp.feature_note.presentation.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state = _state

    private var recentlyDeleteNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(
            noteOrder = NoteOrder.Date(OrderType.Descending)
        )
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                getNotes(event.noteOrder)
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNoteUseCase(note = event.note)
                    recentlyDeleteNote = event.note
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNoteUseCase(note = recentlyDeleteNote ?: return@launch)
                    recentlyDeleteNote = null
                }
            }
            is NotesEvent.ToggleOrderSection -> {
                //aqui pegamos o mesmo valor, copiamos para poder altera-lo
                _state.value = _state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotesUseCase(noteOrder = noteOrder)
            .onEach { notes ->
                _state.value = _state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
    }
}