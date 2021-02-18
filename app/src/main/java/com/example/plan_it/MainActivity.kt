package com.example.plan_it

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.time.Year
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
            //val todoDate = etTodoDate.text.toString()
            if(todoTitle.isNotEmpty()) {
                val todo = Todo((todoTitle.plus(" - ")))//.plus(todoDate)))
                todoAdapter.addTodo(todo)
                etTodoTitle.text.clear()
                //etTodoDate.text.clear()
            }
        }
        btnDate.setOnClickListener {
            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
                Toast.makeText(this, "Year : " + year
                + "\nMonth : " + month
                + "\nDate : " + dayOfMonth, Toast.LENGTH_SHORT).show()
            },
            now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }
        btnTime.setOnClickListener {
            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener {view, hourOfDay, minute ->
                Toast.makeText(this, "Hour : " + hourOfDay
                + "\nMinute : " + minute, Toast.LENGTH_SHORT).show()
            },
            now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true)
            timePicker.show()
        }
    }
}