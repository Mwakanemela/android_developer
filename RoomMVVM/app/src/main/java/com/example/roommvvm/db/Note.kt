package com.example.roommvvm.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val nId: Int,
    @ColumnInfo(name="col_title")val title:String,
    @ColumnInfo(name="col_desc")val desc:String,
)
