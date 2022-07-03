package br.com.notesapp.feature_note.presentation.notes.components

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.notesapp.feature_note.domain.model.Note
import br.com.notesapp.feature_note.presentation.navigation.Screen
import br.com.notesapp.feature_note.presentation.notes.NotesEvent
import br.com.notesapp.feature_note.presentation.notes.NotesState
import br.com.notesapp.feature_note.presentation.notes.NotesViewModel
import br.com.notesapp.feature_note.presentation.util.NoteOrder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NoteContent(
    modifier: Modifier = Modifier,
    state: NotesState,
    scope: CoroutineScope,
    onDeleteNote:(Note) -> Unit,
    onRestoreNote: () -> Unit,
    onFabClicked: (Note) -> Unit = { },
    onToggleOrderSection:() -> Unit,
    onOrderChange:(NoteOrder) -> Unit,
    scaffoldState: ScaffoldState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Suas notas", style = MaterialTheme.typography.h4)
            IconButton(
                onClick = {
                    onToggleOrderSection()
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Sort,
                    contentDescription = "Sort"
                )
            }
        }
        AnimatedVisibility(
            visible = state.isOrderSectionVisible,
            enter = fadeIn() + slideInHorizontally(),
            exit = fadeOut() + slideOutHorizontally()
        ) {
            OrderSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                noteOrder = state.noteOrder,
                onOrderChange = {
                    onOrderChange(it)
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.notes) { note ->
                NotesItem(
                    note = note,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onFabClicked(note)
                        },
                    onDeleteClick = {
                        onDeleteNote(note)
                        scope.launch {
                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                message = "Nota excluída.",
                                actionLabel = "Desfazer"
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                onRestoreNote()
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}