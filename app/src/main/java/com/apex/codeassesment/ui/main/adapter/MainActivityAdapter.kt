package com.apex.codeassesment.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.ui.main.callbacks.MainCallbacks

class MainActivityAdapter(private val mContext: Context, val callback: MainCallbacks) : RecyclerView.Adapter<MainActivityAdapter.MainViewHolder>() {
    var dataList = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.setData(dataList[position], callback)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun addData(users: ArrayList<User>) {
        dataList.clear()
        dataList.addAll(users)
        notifyDataSetChanged()
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun setData(user: User, callback: MainCallbacks) {
            val textView = itemView.findViewById<TextView>(android.R.id.text1)
            textView.text = user.toString()

            textView.setOnClickListener {
                callback.onUserItemClick(user)
            }
        }
    }
}