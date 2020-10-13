package com.example.githubuseryudhadicoding

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private val title: String = "Github User Yudha"
    private val listUser: ArrayList<Users> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setActionBatTitle()

        showRecyclerList()
        getDataUser()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    return true
                }else {
                    listUser.clear()
                    getDataUserSearch(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }


    private fun getDataUser() {
        progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 72e5ac5a6f270bd7335af61bf11e9794c4d0ea03")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                //Jika Koneksi berhasil
                progressBar.visibility = View.INVISIBLE
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
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                //Jika koneksi gagal
                progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getDataDetail(id: String) {
        progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 72e5ac5a6f270bd7335af61bf11e9794c4d0ea03")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$id"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                progressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonObject = JSONObject(result)
                    val name: String? = jsonObject.getString("name").toString()
                    val username: String? = jsonObject.getString("login").toString()
                    val avatar: String? = jsonObject.getString("avatar_url").toString()
                    val followers: Int = jsonObject.getInt("followers")
                    val following: Int = jsonObject.getInt("following")
                    listUser.add(
                        Users(
                            name,
                            username,
                            avatar,
                            followers,
                            following
                        )
                    )
                    showRecyclerList()
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getDataUserSearch(id: String) {
        progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 72e5ac5a6f270bd7335af61bf11e9794c4d0ea03")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/search/users?q=$id"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                progressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonArray = JSONObject(result)
                    val item = jsonArray.getJSONArray("items")
                    for (i in 0 until item.length()) {
                        val jsonObject = item.getJSONObject(i)
                        val username: String = jsonObject.getString("login")
                        getDataDetail(username)
                    }
                }catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setActionBatTitle() {
        if (supportActionBar != null) {
            supportActionBar!!.title = this.title
        }
    }

    private fun showRecyclerList() {
        rv_users.layoutManager = LinearLayoutManager(this)
        rv_users.setHasFixedSize(true)
        val listUserAdapter = ListUserAdapter(listUser)
        rv_users.adapter = listUserAdapter
        listUserAdapter.notifyDataSetChanged()

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                showSelectedUser(data)

            }
        })
    }


    private fun showSelectedUser(users: Users) {
        val toDetails = Intent(this, DetailActivity::class.java)
        toDetails.putExtra(DetailActivity.EXTRA_DATA, users)
        startActivity(toDetails)
    }



}