package com.kelompok6.tasktrack

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kelompok6.tasktrack.database.DatabaseHelper

class DetailtaskActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var tvMatkul: TextView
    private lateinit var tvJudul: TextView
    private lateinit var tvDeskripsi: TextView
    private lateinit var tvDeadline: TextView
    private lateinit var tvStatus: TextView
    private lateinit var btnEdit: Button
    private lateinit var btnDelete: Button

    private var taskId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_task)

        dbHelper = DatabaseHelper(this)

        tvMatkul = findViewById(R.id.tvMatkul)
        tvJudul = findViewById(R.id.tvJudul)
        tvDeskripsi = findViewById(R.id.tvDeskripsi)
        tvDeadline = findViewById(R.id.tvDeadline)
        tvStatus = findViewById(R.id.tvStatus)

        btnEdit = findViewById(R.id.btnEdit)
        btnDelete = findViewById(R.id.btnDelete)

        taskId = intent.getIntExtra("id", -1)

        loadTask()

        btnEdit.setOnClickListener {
            val intent = Intent(
                this,
                AddEditTaskActivity::class.java
            )

            intent.putExtra("id", taskId)

            startActivity(intent)
        }

        btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi")
                .setMessage("Yakin ingin menghapus tugas ini?")
                .setPositiveButton("Ya") { _, _ ->

                    dbHelper.deleteTask(taskId)

                    finish()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    private fun loadTask() {
        val task = dbHelper.getTaskById(taskId)

        task?.let {
            tvMatkul.text = it.mataKuliah
            tvJudul.text = it.judul
            tvDeskripsi.text = it.deskripsi
            tvDeadline.text = "${it.deadline}"
            tvStatus.text = it.status

            when (it.status) {
                "Belum Dikerjakan" -> {
                    tvStatus.setBackgroundResource(
                        R.drawable.status_belum
                    )

                    tvStatus.setTextColor(
                        Color.parseColor("#6B7280")
                    )
                }

                "Sedang Dikerjakan" -> {
                    tvStatus.setBackgroundResource(
                        R.drawable.status_proses
                    )

                    tvStatus.setTextColor(
                        Color.parseColor("#2563EB")
                    )
                }

                "Selesai" -> {
                    tvStatus.setBackgroundResource(
                        R.drawable.status_selesai
                    )

                    tvStatus.setTextColor(
                        Color.parseColor("#059669")
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadTask()
    }
}