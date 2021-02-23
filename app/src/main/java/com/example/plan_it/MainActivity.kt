package com.example.plan_it

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var todoAdapter: TodoAdapter


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoAdapter = TodoAdapter(mutableListOf())
        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        btnAddTodo.setOnClickListener {
            val todoTitle = etTodoTitle.text.toString()
            val todoDate = btnDateTime.text.toString()
            
            var today = Date()

            if(todoTitle.isNotEmpty() && todoDate.isNotEmpty()) {
                val todo = Todo((todoTitle.plus("\n").plus(todoDate)))
                //val todo = Todo((("$dob.time").plus("\n").plus(today.time)))
                //Log.d("today:", "$today.time")
                //Log.d("date: ", "$dob.time")
                todoAdapter.addTodo(todo)
                etTodoTitle.text.clear()
                btnDateTime.text.clear()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun openDateTimePicker(view: View) {
        var c = Calendar.getInstance()
        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, yy, mm, dd ->
            var dt = "$dd/${mm+1}/$yy"
            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, hh, mi ->
                dt += " $hh:$mi"
                btnDateTime.setText(dt)
            },c.get(Calendar.HOUR), c.get(Calendar.MINUTE),false).show()
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
    }
}


