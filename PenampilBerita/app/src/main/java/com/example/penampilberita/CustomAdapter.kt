package com.example.penampilberita

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter (val userList: ArrayList<User>): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val user: User=userList[position]
        holder?.textViewId?.text = user.id
        holder?.textViewJudul?.text = user.judul
        holder?.textViewWaktu?.text = user.waktu
        holder?.textViewPenulis?.text = user.penulis
        holder?.textViewIsi?.text = user.isi

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent?.context).inflate(R.layout.list_layout, parent, false)
        return  ViewHolder(v)

    }

    override fun getItemCount(): Int {

        return userList.size
    }

    class  ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val textViewId = itemView.findViewById(R.id.textViewId) as TextView
        val textViewJudul = itemView.findViewById(R.id.textViewJudul) as TextView
        val textViewWaktu = itemView.findViewById(R.id.textViewWaktu) as TextView
        val textViewPenulis = itemView.findViewById(R.id.textViewPenulis) as TextView
        val textViewIsi = itemView.findViewById(R.id.textViewIsi) as TextView

    }

}