package com.example.githubuseryudhadicoding.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.githubuseryudhadicoding.R
import com.example.githubuseryudhadicoding.model.Users
import com.example.githubuseryudhadicoding.viewmodel.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.username

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private val title: String = "Detail User"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setActionBarTitle()
        setUser()

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f

    }

    private fun setActionBarTitle() {
        if (supportActionBar != null) {
            supportActionBar!!.title = title
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUser() {
        val users = intent.getParcelableExtra<Users>(EXTRA_DATA) as Users
        user_name.text = users.usersName.toString()
        username.text = "@ " + users.username.toString()
        Glide.with(this).load(users.avatar.toString()).into(avatar)
    }

}