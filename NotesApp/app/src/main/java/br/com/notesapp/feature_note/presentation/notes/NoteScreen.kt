package br.com.notesapp.feature_note.presentation.notes

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.notesapp.feature_note.presentation.navigation.Screen
import br.com.notesapp.feature_note.presentation.notes.components.NoteContent

@Composable
fun NoteScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditNoteScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        scaffoldState = scaffoldState,
        content = { paddingValues ->
            NoteContent(
                modifier = Modifier.padding(paddingValues),
                state = state,
                scope = scope,
                scaffoldState = scaffoldState,
                onDeleteNote = {
                    viewModel.onEvent(NotesEvent.DeleteNote(it))
                },
                onRestoreNote = {
                    viewModel.onEvent(NotesEvent.RestoreNote)
                },
                onFabClicked = {
                    navController.navigate(
                        Screen.AddEditNoteScreen.passIds(
                            noteId = it.id,
                            noteColor = it.color
                        )
                    )
                },
                onToggleOrderSection = {
                    viewModel.onEvent(NotesEvent.ToggleOrderSection)
                },
                onOrderChange = {
                    viewModel.onEvent(NotesEvent.Order(it))
                }
            )
        }
    )
}