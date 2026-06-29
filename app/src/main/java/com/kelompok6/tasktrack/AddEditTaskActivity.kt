package com.kelompok6.tasktrack

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kelompok6.tasktrack.database.DatabaseHelper
import com.kelompok6.tasktrack.model.Task
import android.app.DatePickerDialog
import java.util.Calendar
import android.widget.TextView

class AddEditTaskActivity : AppCompatActivity() {

    private lateinit var etMatkul: EditText
    private lateinit var etJudul: EditText
    private lateinit var etDeskripsi: EditText
    private lateinit var etDeadline: EditText
    private lateinit var spStatus: Spinner
    private lateinit var btnSave: Button
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var tvFormTitle: TextView

    private var taskId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_task)

        dbHelper = DatabaseHelper(this)

        etMatkul = findViewById(R.id.etMatkul)
        etJudul = findViewById(R.id.etJudul)
        etDeskripsi = findViewById(R.id.etDeskripsi)
        etDeadline = findViewById(R.id.etDeadline)
        spStatus = findViewById(R.id.spStatus)
        btnSave = findViewById(R.id.btnSave)
        tvFormTitle = findViewById(R.id.tvFormTitle)

        setupSpinner()

        taskId = intent.getIntExtra("id", -1)

        if (taskId != -1) {
            tvFormTitle.text = "Edit Tugas"

            val task = dbHelper.getTaskById(taskId)

            task?.let {
                etMatkul.setText(it.mataKuliah)
                etJudul.setText(it.judul)
                etDeskripsi.setText(it.deskripsi)
                etDeadline.setText(it.deadline)

                val posisi = when (it.status) {
                    "Belum Dikerjakan" -> 0
                    "Sedang Dikerjakan" -> 1
                    else -> 2
                }

                spStatus.setSelection(posisi)
                btnSave.text = "UPDATE"
            }
        } else {
            tvFormTitle.text = "Tambah Tugas"
        }

        etDeadline.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                this,
                { _, y, m, d ->

                    etDeadline.setText(
                        "$d/${m + 1}/$y"
                    )
                },
                year,
                month,
                day
            )

            datePicker.show()
        }

        btnSave.setOnClickListener {
            val matkul = etMatkul.text.toString().trim()
            val judul = etJudul.text.toString().trim()
            val deskripsi = etDeskripsi.text.toString().trim()
            val deadline = etDeadline.text.toString().trim()
            val status = spStatus.selectedItem.toString()

            if (
                matkul.isEmpty() ||
                judul.isEmpty() ||
                deskripsi.isEmpty() ||
                deadline.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Semua data harus diisi",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val task = Task(
                id = taskId,
                mataKuliah = matkul,
                judul = judul,
                deskripsi = deskripsi,
                deadline = deadline,
                status = status
            )

            val success = if (taskId == -1) {
                dbHelper.insertTask(task)
            } else {
                dbHelper.updateTask(task)
            }

            if (success) {
                Toast.makeText(
                    this,
                    if (taskId == -1)
                        "Tugas berhasil disimpan"
                    else
                        "Tugas berhasil diupdate",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Operasi gagal",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupSpinner() {
        val statusList = arrayOf(
            "Belum Dikerjakan",
            "Sedang Dikerjakan",
            "Selesai"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            statusList
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spStatus.adapter = adapter
    }

}