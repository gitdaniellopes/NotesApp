package br.com.notesapp.di

import android.content.Context
import androidx.room.Room
import br.com.notesapp.feature_note.data.data_source.NoteDatabase
import br.com.notesapp.feature_note.data.data_source.NoteDatabase.Companion.DATABASE_NAME
import br.com.notesapp.feature_note.data.repository.NoteRepositoryImpl
import br.com.notesapp.feature_note.domain.repository.NoteRepository
import br.com.notesapp.feature_note.domain.use_case.AddNoteUseCase
import br.com.notesapp.feature_note.domain.use_case.DeleteNoteUseCase
import br.com.notesapp.feature_note.domain.use_case.GetNotesUseCase
import br.com.notesapp.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun providesNoteRepository(database: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(noteDao = database.noteDao)
    }

    @Singleton
    @Provides
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository = repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository = repository),
            addNoteUseCase = AddNoteUseCase(repository = repository)
        )
    }
}