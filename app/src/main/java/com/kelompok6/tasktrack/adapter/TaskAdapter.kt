package com.kelompok6.tasktrack.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kelompok6.tasktrack.R
import com.kelompok6.tasktrack.model.Task

class TaskAdapter(
    private var taskList: ArrayList<Task>,
    private val onItemClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvMatkul: TextView =
            itemView.findViewById(R.id.tvMatkul)
        val tvDeadline: TextView =
            itemView.findViewById(R.id.tvDeadline)
        val tvStatus: TextView =
            itemView.findViewById(R.id.tvStatus)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_task,
                parent,
                false
            )

        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int
    ) {

        val task = taskList[position]

        holder.tvMatkul.text = task.mataKuliah

        holder.tvDeadline.text =
            "Deadline: ${task.deadline}"

        holder.tvStatus.text = task.status

        when (task.status) {
            "Belum Dikerjakan" -> {
                holder.tvStatus.setBackgroundResource(
                    R.drawable.status_belum
                )

                holder.tvStatus.setTextColor(
                    Color.parseColor("#6B7280")
                )
            }

            "Sedang Dikerjakan" -> {
                holder.tvStatus.setBackgroundResource(
                    R.drawable.status_proses
                )

                holder.tvStatus.setTextColor(
                    Color.parseColor("#2563EB")
                )
            }

            "Selesai" -> {
                holder.tvStatus.setBackgroundResource(
                    R.drawable.status_selesai
                )

                holder.tvStatus.setTextColor(
                    Color.parseColor("#059669")
                )
            }
        }

        holder.itemView.setOnClickListener {
            onItemClick(task)
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun updateData(newList: ArrayList<Task>) {
        taskList = newList
        notifyDataSetChanged()
    }
}