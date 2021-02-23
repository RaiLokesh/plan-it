package com.example.plan_it
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import kotlinx.android.synthetic.main.item_todo.view.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter (
        private val items: MutableList<Todo>

) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_todo,
                        parent,
                        false
                )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun addTodo(todo: Todo){
        items.add(todo)
        notifyItemInserted(items.size - 1) //gotta change here to sort
    }
    fun delTodos(){
        items.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }
    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
        if (isChecked) {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        }else{
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = items[position]
        holder.itemView.apply {
            tvTodoTitle.text = curTodo.title
            cbDone.isChecked = curTodo.isChecked
            toggleStrikeThrough(tvTodoTitle, curTodo.isChecked)
            cbDone.setOnCheckedChangeListener { _, ischecked ->
                toggleStrikeThrough(tvTodoTitle, ischecked)
                curTodo.isChecked = !curTodo.isChecked
            }
        }
    }
}