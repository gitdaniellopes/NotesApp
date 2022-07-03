package br.com.notesapp.feature_note.presentation.navigation

sealed class Screen(val route: String) {
    object NotesScreen : Screen("notes_screen")
    object AddEditNoteScreen :
        Screen("add_edit_note_screen?noteId={noteId}&noteColor={noteColor}") {
        fun passIds(noteId: Int?, noteColor: Int?): String {
            return "add_edit_note_screen?noteId=$noteId&noteColor=$noteColor"
        }
    }
}
