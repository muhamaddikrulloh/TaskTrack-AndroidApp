package com.kelompok6.tasktrack.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.kelompok6.tasktrack.model.Task

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "tasktrack.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_TASK = "tasks"
        private const val COL_ID = "id"
        private const val COL_MATKUL = "mata_kuliah"
        private const val COL_JUDUL = "judul"
        private const val COL_DESKRIPSI = "deskripsi"
        private const val COL_DEADLINE = "deadline"
        private const val COL_STATUS = "status"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_TASK(
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_MATKUL TEXT,
                $COL_JUDUL TEXT,
                $COL_DESKRIPSI TEXT,
                $COL_DEADLINE TEXT,
                $COL_STATUS TEXT
            )
        """.trimIndent()

        db.execSQL(createTable)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TASK")
        onCreate(db)
    }

    // CREATE
    fun insertTask(task: Task): Boolean {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COL_MATKUL, task.mataKuliah)
            put(COL_JUDUL, task.judul)
            put(COL_DESKRIPSI, task.deskripsi)
            put(COL_DEADLINE, task.deadline)
            put(COL_STATUS, task.status)
        }

        val result = db.insert(TABLE_TASK, null, values)

        db.close()

        return result != -1L
    }

    // READ
    fun getAllTasks(): ArrayList<Task> {
        val taskList = ArrayList<Task>()

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_TASK ORDER BY id DESC",
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val task = Task(
                    id = cursor.getInt(0),
                    mataKuliah = cursor.getString(1),
                    judul = cursor.getString(2),
                    deskripsi = cursor.getString(3),
                    deadline = cursor.getString(4),
                    status = cursor.getString(5)
                )

                taskList.add(task)

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return taskList
    }

    fun getTaskById(id: Int): Task? {
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM tasks WHERE id=?",
            arrayOf(id.toString())
        )

        var task: Task? = null

        if (cursor.moveToFirst()) {
            task = Task(
                id = cursor.getInt(0),
                mataKuliah = cursor.getString(1),
                judul = cursor.getString(2),
                deskripsi = cursor.getString(3),
                deadline = cursor.getString(4),
                status = cursor.getString(5)
            )
        }

        cursor.close()
        db.close()

        return task
    }

    // UPDATE
    fun updateTask(task: Task): Boolean {
        val db = writableDatabase

        val values = ContentValues().apply {

            put(COL_MATKUL, task.mataKuliah)
            put(COL_JUDUL, task.judul)
            put(COL_DESKRIPSI, task.deskripsi)
            put(COL_DEADLINE, task.deadline)
            put(COL_STATUS, task.status)

        }

        val result = db.update(
            TABLE_TASK,
            values,
            "$COL_ID=?",
            arrayOf(task.id.toString())
        )

        db.close()

        return result > 0
    }

    // DELETE
    fun deleteTask(id: Int): Boolean {
        val db = writableDatabase

        val result = db.delete(
            TABLE_TASK,
            "$COL_ID=?",
            arrayOf(id.toString())
        )

        db.close()

        return result > 0
    }
}