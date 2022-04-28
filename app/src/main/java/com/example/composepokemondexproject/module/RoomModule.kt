package com.example.composepokemondexproject.module

import android.content.Context
import androidx.room.Room
import com.example.composepokemondexproject.data.database.AppDatabase
import com.example.composepokemondexproject.data.database.dao.PokemonDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Create by Nguyen Thanh Toan on 11/11/21
 *
 */

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun providePokemonDAO(database: AppDatabase): PokemonDAO =
        database.pokemonDAO()
}
