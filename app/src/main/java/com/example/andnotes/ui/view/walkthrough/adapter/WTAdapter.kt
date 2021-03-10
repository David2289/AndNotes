package com.example.andnotes.ui.view.walkthrough.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.andnotes.R
import com.example.andnotes.ui.view.walkthrough.model.WTItem

class WTAdapter(private val itemList: ArrayList<WTItem>): RecyclerView.Adapter<WTAdapter.WTViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WTViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rootView = layoutInflater.inflate(R.layout.item_walkthrough, parent, false)
        return WTViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: WTViewHolder, position: Int) {
        val item = itemList[position]
        holder.image.setImageResource(item.imageRes)
        holder.title.setText(item.titleRes)
        holder.desc.setText(item.descRes)
    }


    class WTViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image)
        val title: TextView = view.findViewById(R.id.title)
        val desc: TextView = view.findViewById(R.id.desc)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}