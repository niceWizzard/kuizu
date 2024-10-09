package com.coderizzard.core.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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




}