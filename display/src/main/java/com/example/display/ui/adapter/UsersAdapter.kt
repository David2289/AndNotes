package com.example.display.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.display.R
import com.example.display.business.model.User
import com.squareup.picasso.Picasso

class UsersAdapter(var userList: List<User>, val onItemClick: (user: User) -> Unit) : RecyclerView.Adapter<UsersAdapter.UserVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        val inflater = LayoutInflater.from(parent.context)
        val rootView = inflater.inflate(R.layout.item_user, parent, false)
        return UserVH(rootView)
    }

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        val user = userList.get(position)
        Picasso.get().load(user.avatar).into(holder.photo)
        val completeName = user.firstName + " " + user.lastName
        holder.name.text = completeName
        holder.view.setOnClickListener { onItemClick(user) }
    }

    class UserVH(val view: View): RecyclerView.ViewHolder(view) {
        val photo: ImageView = view.findViewById(R.id.photo)
        val name: TextView = view.findViewById(R.id.name)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}