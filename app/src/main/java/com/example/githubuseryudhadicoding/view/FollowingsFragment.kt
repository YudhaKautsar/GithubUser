package com.example.githubuseryudhadicoding.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuseryudhadicoding.R
import com.example.githubuseryudhadicoding.model.Users
import com.example.githubuseryudhadicoding.viewmodel.ListFollowingAdapter
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_follower.*
import kotlinx.android.synthetic.main.fragment_following.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class FollowingsFragment : Fragment() {

    companion object {
        private val TAG = FollowingsFragment::class.java.simpleName
        const val EXTRA_DATA = "extra_data"
    }

    private val listUser: ArrayList<Users> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listUser.clear()
        val dataUser = activity!!.intent.getParcelableExtra<Users>(EXTRA_DATA) as Users
        getDataUser(dataUser.username.toString())
    }

    private fun getDataUser(login: String) {
        progressBarFollowings.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 72e5ac5a6f270bd7335af61bf11e9794c4d0ea03")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$login/following"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                progressBarFollowings.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username: String = jsonObject.getString("login")
                        getDataDetail(username)
                    }
                }catch (e: Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                progressBarFollowers.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun getDataDetail(login: String) {
        progressBarFollowings.visibility = View.VISIBLE
        val client =AsyncHttpClient()
        client.addHeader("Authorization", "token 72e5ac5a6f270bd7335af61bf11e9794c4d0ea03")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$login"
        client.get(url, object  : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                progressBarFollowings.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonObject = JSONObject(result)
                    val name: String? = jsonObject.getString("name").toString()
                    val username: String? = jsonObject.getString("login").toString()
                    val avatar: String? = jsonObject.getString("avatar_url").toString()
                    listUser.add(
                        Users(
                            name,
                            username,
                            avatar
                        )
                    )
                    showRecyclerList()
                } catch (e: Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                progressBarFollowers.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()

            }

        })
    }

    private fun showRecyclerList() {
        rv_followings.layoutManager = LinearLayoutManager(activity)
        rv_followings.setHasFixedSize(true)
        val listFollowingAdapter = ListFollowingAdapter(listUser)
        rv_followings.adapter = listFollowingAdapter
        listFollowingAdapter.notifyDataSetChanged()


        listFollowingAdapter.setOnItemClickCallback(object : ListFollowingAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                showSelectedUser(data)

            }
        })
    }

    private fun showSelectedUser(users: Users) {
        val toDetails = Intent(activity, DetailActivity::class.java)
        toDetails.putExtra(DetailActivity.EXTRA_DATA, users)
        startActivity(toDetails)
    }

}