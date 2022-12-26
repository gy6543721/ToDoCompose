package levilin.todocompose.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import levilin.todocompose.data.model.Priority
import levilin.todocompose.utility.ConstantValue
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = ConstantValue.PREFERENCE_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {
    private object PreferenceKey {
        val sortKey = stringPreferencesKey(name = ConstantValue.PREFERENCE_KEY)
    }
    private val dataStore = context.dataStore

    suspend fun persistSortState(priority: Priority) {
        dataStore.edit { mutablePreferences ->  
            mutablePreferences[PreferenceKey.sortKey] = priority.name
        }
    }

    val readDataStore: Flow<String> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { mutablePreferences ->
        val sortState = mutablePreferences[PreferenceKey.sortKey] ?: Priority.NO.name
        sortState
    }
}