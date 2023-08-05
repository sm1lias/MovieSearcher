package com.smilias.movierama.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.smilias.movierama.domain.preferences.MyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MyPreferencesImpl @Inject constructor(
    private val sharedPref: DataStore<Preferences>
): MyPreferences {

    override fun getFavorites(): Flow<Set<String>> {
        val FAVORITE = stringSetPreferencesKey(MyPreferences.KEY_FAVORITE)
        return sharedPref.data.
                map {
                    it[FAVORITE] ?: emptySet()
                }
    }

    override suspend fun saveFavorites(list: Set<String>) {
        val FAVORITE = stringSetPreferencesKey(MyPreferences.KEY_FAVORITE)
        sharedPref.edit {prefs ->
            prefs[FAVORITE] = list
        }
    }

}