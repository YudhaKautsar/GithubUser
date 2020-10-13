package com.example.githubuseryudhadicoding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_row_user.view.*
import kotlin.collections.ArrayList

class ListUserAdapter(private val listUser: ArrayList<Users>, private var onItemClickCallback: OnItemClickCallback? = null) : RecyclerView.Adapter<ListUserAdapter.UserViewHolder>() {

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(users: Users) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(users.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(user_avatar)

                users_name.text = users.usersName
                username.text = users.username
                user_followers.text = users.userFollowers.toString().trim()
                user_following.text = users.userFollowing.toString().trim()

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(users) }
            }

        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Users)
    }

}