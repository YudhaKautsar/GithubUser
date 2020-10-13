package com.example.githubuseryudhadicoding

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.username
import kotlinx.android.synthetic.main.item_row_user.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f

    }

    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            supportActionBar!!.title = title
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUser() {
        val users = intent.getParcelableExtra<Users>(EXTRA_DATA) as Users
        setActionBarTitle(users.usersName.toString())
        user_name.text = users.usersName.toString()
        username.text = "@ " + users.username.toString()
        user_followers.text = users.userFollowers.toString()
        user_following.text = users.userFollowing.toString()
        Glide.with(this).load(users.avatar.toString()).into(avatar)
    }

}