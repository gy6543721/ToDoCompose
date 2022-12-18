package levilin.todocompose.dependency

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import levilin.todocompose.data.ToDoDatabase
import levilin.todocompose.utility.ConstantValue.DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(context, ToDoDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDAO(database: ToDoDatabase) = database.toDoDAO()
}