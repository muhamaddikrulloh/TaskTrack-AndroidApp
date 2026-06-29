package com.kelompok6.tasktrack.model

data class Task(
    var id: Int = 0,
    var mataKuliah: String,
    var judul: String,
    var deskripsi: String,
    var deadline: String,
    var status: String
)