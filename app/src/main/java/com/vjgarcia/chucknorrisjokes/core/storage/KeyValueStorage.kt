package com.vjgarcia.chucknorrisjokes.core.storage

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import java.lang.reflect.Type

class KeyValueStorage(
    private val sharedPreferences: SharedPreferences,
    private val moshi: Moshi
) {

    private val sharedPreferencesEditor: SharedPreferences.Editor
        get() = sharedPreferences.edit()

    fun <T> putObject(key: String, instance: T?, type: Type) {
        val instanceMoshiAdapter = moshi.adapter<T>(type)
        val serializedObject = instanceMoshiAdapter.toJson(instance)
        sharedPreferencesEditor.putString(key, serializedObject).commit()
    }

    fun <T> getObject(key: String, type: Type): T? {
        val instanceMoshiAdapter = moshi.adapter<T>(type)
        val serializedObject = sharedPreferences.getString(key, null)

        return if (serializedObject == null) {
            null
        } else {
            instanceMoshiAdapter.fromJson(serializedObject)
        }
    }

    fun remove(key: String) {
        sharedPreferencesEditor.remove(key).commit()
    }
}