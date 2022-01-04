package com.gketdev.xkcdreader.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "xkcditems")
data class XkcdEntity(
    @PrimaryKey
    val xkcdId : Int,
    val title : String,
    val alt : String,
    val image : String,
)