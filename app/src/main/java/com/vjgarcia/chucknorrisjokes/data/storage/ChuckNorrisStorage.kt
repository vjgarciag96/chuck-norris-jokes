package com.vjgarcia.chucknorrisjokes.data.storage

import com.vjgarcia.chucknorrisjokes.core.storage.KeyValueStorage
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ChuckNorrisStorage(
    private val keyValueStorage: KeyValueStorage,
    private val defaultCategoriesStorage: DefaultCategoriesStorage
) {

    var categories: List<String>
        get() = keyValueStorage.getObject(CATEGORIES_KEY, listOfStringType) ?: defaultCategoriesStorage.defaultCategories
        set(value) {
            keyValueStorage.putObject(CATEGORIES_KEY, value, listOfStringType)
        }

    private val listOfStringType: Type by lazy {
        object : ParameterizedType {
            override fun getRawType(): Type = List::class.java

            override fun getOwnerType(): Type? = null

            override fun getActualTypeArguments(): Array<Type> = arrayOf(String::class.java)
        }
    }

    private companion object {
        const val CATEGORIES_KEY = "jokes:categories"
    }
}