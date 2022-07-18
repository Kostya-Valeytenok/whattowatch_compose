package com.raproject.whattowatch.repository.app_settings

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.raproject.whattowatch.repository.app_settings.AbstractPreferences.Serializer.To
import com.raproject.whattowatch.utils.AppState
import com.raproject.whattowatch.utils.TableRow
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class AbstractPreferences(private val name: String, context: Context) {

    var errorCallback: ((Throwable) -> Unit)? = null

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    fun clear() {
        sharedPreferences.edit { clear() }
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T> getValue(key: String, defaultValue: T?): T? {
        val value = sharedPreferences.all[key]
        return if (value != null) {
            value as T
        } else {
            setValue(key, defaultValue)
            defaultValue
        }
    }

    protected fun <T> setValue(key: String, value: T?) {
        sharedPreferences.edit {
            if (value == null) {
                remove(key)
                return@edit
            }

            when (value) {
                is String -> putString(key, value)
                is Boolean -> putBoolean(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is Int -> putInt(key, value)
                is Collection<*> -> putStringSet(key, value.map { it.toString() }.toSet())
                else -> Log.e(
                    "Settings",
                    "\"$value for $key is not supported. Use PreferencesDelegateWithSerializer or PreferencesDelegateNullableWithSerializer\""
                )
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T, R : To> getValue(
        key: String,
        defaultValue: T?,
        serializer: Serializer<T, R>
    ): T? {
        val value = sharedPreferences.all[key]?.let { serializer.deserialize(it) }
        return if (value != null) {
            value
        } else {
            setValue(key, defaultValue, serializer)
            defaultValue
        }
    }

    protected fun <T, R : To> setValue(key: String, value: T?, serializer: Serializer<T, R>) {
        sharedPreferences.edit {
            if (value == null) {
                remove(key)
                return@edit
            }

            when (val convertedValue = serializer.serialize(value)) {
                is To.String -> putString(key, convertedValue.string)
                is To.Boolean -> putBoolean(key, convertedValue.boolean)
                is To.Long -> putLong(key, convertedValue.long)
                is To.Float -> putFloat(key, convertedValue.float)
                is To.Int -> putInt(key, convertedValue.int)
                is To.StringSet -> putStringSet(key, convertedValue.stringSet)
            }
        }
    }

    protected inner class PreferencesDelegate<T>(
        private val key: String,
        private val defaultValue: T
    ) : ReadWriteProperty<Any, T> {

        override fun getValue(thisRef: Any, property: KProperty<*>): T {
            return getValue(key, defaultValue) ?: defaultValue
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            setValue(key, value)
        }
    }

    protected inner class PreferencesDelegateWithSerializer<T, R : To>(
        private val key: String,
        private val defaultValue: T,
        private val serializer: Serializer<T, R>
    ) : ReadWriteProperty<Any, T> {

        override fun getValue(thisRef: Any, property: KProperty<*>): T {
            return getValue(key, defaultValue, serializer) ?: defaultValue
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            setValue(key, value, serializer)
        }

    }

    protected inner class PreferencesDelegateNullable<T>(private val key: String) :
        ReadWriteProperty<Any, T?> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T? {
            return getValue<T>(key, null)
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
            setValue(key, value)
        }
    }

    protected inner class PreferencesDelegateNullableWithSerializer<T, R : To>(
        private val key: String,
        private val serializer: Serializer<T, R>
    ) : ReadWriteProperty<Any, T?> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T? {
            return getValue(key, null, serializer)
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
            setValue(key, value, serializer)
        }
    }

    interface Serializer<T, R : To> {

        //Instant
        object Instant : Serializer<java.time.Instant, To.Long> {
            override fun serialize(from: java.time.Instant) = To.Long(from.toEpochMilli())
            override fun deserialize(value: Any) =
                runCatching { java.time.Instant.ofEpochMilli(value as Long) }.onFailure {
                    AppState.settingsErrorCallback.invoke(it)
                }.getOrNull()
        }

        object Localization : Serializer<com.raproject.whattowatch.utils.Localization, To.String> {

            override fun serialize(from: com.raproject.whattowatch.utils.Localization) =
                To.String(from.toString())


            override fun deserialize(value: Any): com.raproject.whattowatch.utils.Localization? =
                runCatching {
                    com.raproject.whattowatch.utils.Localization.valueOf(value as String)
                }.onFailure { AppState.settingsErrorCallback.invoke(it) }.getOrNull()
        }

        object OrderType : Serializer<com.raproject.whattowatch.utils.OrderType, To.String> {

            override fun serialize(from: com.raproject.whattowatch.utils.OrderType) =
                To.String(from.toString())

            override fun deserialize(value: Any): com.raproject.whattowatch.utils.OrderType? =
                runCatching {
                    com.raproject.whattowatch.utils.OrderType.valueOf(value as String)
                }.onFailure { AppState.settingsErrorCallback.invoke(it) }.getOrNull()
        }

        object OrderedRow : Serializer<TableRow, To.String> {
            override fun serialize(from: TableRow): To.String = To.String(from.name)

            override fun deserialize(value: Any): TableRow? {
                return runCatching { TableRow(value as String) }.onFailure {
                    AppState.settingsErrorCallback.invoke(
                        it
                    )
                }.getOrNull()
            }


        }

        fun serialize(from: T): R
        fun deserialize(value: Any): T?

        sealed class To {
            class String(val string: kotlin.String) : To()
            class Boolean(val boolean: kotlin.Boolean) : To()
            class Long(val long: kotlin.Long) : To()
            class Float(val float: kotlin.Float) : To()
            class Int(val int: kotlin.Int) : To()
            class StringSet(val stringSet: Set<kotlin.String>) : To()
        }
    }
}