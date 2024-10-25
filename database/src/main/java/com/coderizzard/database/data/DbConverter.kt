package com.coderizzard.database.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDateTime

class DbConverter  {

    @TypeConverter
    fun fromListStringToString(v : List<String>) : String {
        return Gson().toJson(v)
    }

    @TypeConverter
    fun fromStringToListString(value: String?): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromListIntToString(v : List<Int>) : String {
        return Gson().toJson(v)
    }

    @TypeConverter
    fun fromStringToListInt(value: String?): List<Int>? {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromStringToLocalDateTime(value : String?) : LocalDateTime {
        return LocalDateTime.parse(value)
    }

    @TypeConverter
    fun fromLocalDateTimeToString(v : LocalDateTime) : String {
        return v.toString()
    }


}