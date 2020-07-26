package com.example.githubusers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*

class UserRecyclerAdapter(private val userList : ArrayList<User>): RecyclerView.Adapter<UserRecyclerAdapter.UserViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_user,
            parent,
            false
        )
        return UserViewHolder(itemView)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.itemView.githubUsername.text = userList[position].login
        holder.itemView.githubUrl.text = userList[position].url
        holder.itemView.githubUserId.text = userList[position].id.toString()
        holder.itemView.githubUserScore.text = userList[position].score.toString()
        Picasso.get()
            .load(userList[position].avatar_url)
            .placeholder(R.drawable.ic_launcher_background)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.itemView.ivGithubUser);
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}