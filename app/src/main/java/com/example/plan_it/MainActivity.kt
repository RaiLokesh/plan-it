package com.example.plan_it

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.time.Year
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var todoAdapter: TodoAdapter
    /*var date: Int = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var min: Int = 0*/

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoAdapter = TodoAdapter(mutableListOf())
        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        btnAddTodo.setOnClickListener {
            val todoTitle = etTodoTitle.text.toString()
            //val todoDate = etTodoDate.text.toString()
            if(todoTitle.isNotEmpty()) {
                val todo = Todo(todoTitle)//.plus(todoDate)))
                todoAdapter.addTodo(todo)
                etTodoTitle.text.clear()
                btnDateTime.text.clear()
                //etTodoDate.text.clear()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun openDateTimePicker(view: View) {
        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, yy, mm, dd ->
            var dt = "$dd/$mm/$yy"
            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, hh, mi ->
                dt = "$dt  $hh:$mi"
                btnDateTime.setText(dt)
            },10,15,true).show()
        }, 2021,2,18).show()
    }
}