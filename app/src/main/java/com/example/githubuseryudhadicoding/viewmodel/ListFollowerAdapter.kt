package com.example.githubuseryudhadicoding.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuseryudhadicoding.R
import com.example.githubuseryudhadicoding.model.Users
import kotlinx.android.synthetic.main.item_row_fragment.view.*

class ListFollowerAdapter(private val listUser: ArrayList<Users>, private var onItemClickCallback: OnItemClickCallback? = null) : RecyclerView.Adapter<ListFollowerAdapter.FollowerViewHolder>(){


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_fragment, parent, false)
        return FollowerViewHolder(view)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    inner class FollowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(users: Users) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(users.avatar)
                    .apply(RequestOptions().override(50, 50))
                    .into(fragment_avatar)

                users_name_fragment.text = users.usersName
                username_fragment.text = users.username

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(users) }
            }
        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Users)
    }


}