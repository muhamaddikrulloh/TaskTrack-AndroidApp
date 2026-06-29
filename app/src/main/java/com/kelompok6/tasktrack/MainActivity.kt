package com.kelompok6.tasktrack

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kelompok6.tasktrack.adapter.TaskAdapter
import com.kelompok6.tasktrack.database.DatabaseHelper
import com.kelompok6.tasktrack.model.Task

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter
    private lateinit var etSearch: EditText
    private lateinit var spFilter: Spinner
    private lateinit var fabAdd: FloatingActionButton

    private var taskList = ArrayList<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        recyclerView = findViewById(R.id.rvTasks)
        etSearch = findViewById(R.id.etSearch)
        spFilter = findViewById(R.id.spFilter)
        fabAdd = findViewById(R.id.fabAdd)

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = TaskAdapter(taskList) { task ->

            val intent = Intent(
                this,
                DetailtaskActivity::class.java
            )

            intent.putExtra("id", task.id)

            startActivity(intent)
        }

        recyclerView.adapter = adapter

        setupSpinner()
        loadData()
        setupSearch()

        fabAdd.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AddEditTaskActivity::class.java
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        taskList = dbHelper.getAllTasks()
        adapter.updateData(taskList)
    }

    private fun setupSpinner() {

        val statusList = arrayOf(
            "Semua",
            "Belum Dikerjakan",
            "Sedang Dikerjakan",
            "Selesai"
        )

        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            statusList
        )

        spinnerAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spFilter.adapter = spinnerAdapter

        spFilter.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: android.view.View?,
                    position: Int,
                    id: Long
                ) {

                    val selected =
                        parent?.getItemAtPosition(position).toString()

                    if (selected == "Semua") {
                        adapter.updateData(
                            dbHelper.getAllTasks()
                        )
                    } else {

                        val filtered = ArrayList<Task>()

                        for (task in dbHelper.getAllTasks()) {
                            if (task.status == selected) {
                                filtered.add(task)
                            }
                        }

                        adapter.updateData(filtered)
                    }
                }

                override fun onNothingSelected(
                    parent: AdapterView<*>?
                ) {
                }
            }
    }

    private fun setupSearch() {

        etSearch.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                    val keyword =
                        s.toString().lowercase()

                    val filteredList =
                        ArrayList<Task>()

                    for (task in dbHelper.getAllTasks()) {
                        if (
                            task.mataKuliah
                                .lowercase()
                                .contains(keyword)
                            ||
                            task.judul
                                .lowercase()
                                .contains(keyword)
                        ) {
                            filteredList.add(task)
                        }
                    }

                    adapter.updateData(filteredList)
                }

                override fun afterTextChanged(
                    s: Editable?
                ) {
                }
            }
        )
    }
}