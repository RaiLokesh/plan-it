package com.example.plan_it

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.io.FileOutputStream
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var todoAdapter: TodoAdapter

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getUsers()
        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        btnAddTodo.setOnClickListener {
            val todoTitle = etTodoTitle.text.toString()
            val todoDate = btnDateTime.text.toString()

            if(todoTitle.isNotEmpty() && todoDate.isNotEmpty()) {
                val todo = Todo(todoTitle, "Deadline: ".plus(todoDate))

                todoAdapter.addTodo(todo)
                etTodoTitle.text.clear()
                btnDateTime.text.clear()
            }
        }
        deletemarked.setOnClickListener{
            todoAdapter.delTodos()
        }
        save_changes.setOnClickListener{
            saveUsersToPreferences()
        }
    }

    
    private fun saveUsersToPreferences(){
        val prefEditor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        val jsonString = Gson().toJson(todoAdapter.items)
        prefEditor.putString("users", jsonString).apply()
    }
    private fun getUsers(){
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val jsonString = preferences.getString("users", null)

        if (jsonString!=null){
            val it = object : TypeToken<MutableList<Todo>>() {}.type
            val list: MutableList<Todo> = Gson().fromJson(jsonString, it)
            todoAdapter = TodoAdapter(list)
        }else{
            todoAdapter = TodoAdapter(mutableListOf())
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun openDateTimePicker(view: View) {
        var c = Calendar.getInstance()
        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, yy, mm, dd ->
            var dt : String = ""
            dt += "$yy/"
            dt += if("${mm+1}".length<2) "0${mm+1}/"
            else "${mm+1}/"
            dt += if("$dd".length<2) "0$dd"
            else "$dd"
            
            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, hh, mi ->
                dt += " "
                dt += if("$hh".length<2) "0$hh:"
                else "$hh:"
                dt += if("$mi".length < 2) "0$mi"
                else "$mi"
                btnDateTime.setText(dt)
            },c.get(Calendar.HOUR), c.get(Calendar.MINUTE),false).show()
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
    }
}
