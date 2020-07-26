package com.example.githubusers

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.net.URL
import java.util.*


const val TAG = "DEBUG_APP"
class MainActivity : AppCompatActivity() {

    var listOfUsers = arrayListOf<User>()

    lateinit var customAdapter : UserRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvUsers.layoutManager=LinearLayoutManager(this)
        customAdapter = UserRecyclerAdapter(listOfUsers)
        rvUsers.adapter = customAdapter

        btnSearch.setOnClickListener {
            listOfUsers.clear()
            val username =  etUsername.text.toString()
            if(username.isEmpty()){
                Toast.makeText(baseContext, "Enter valid username", Toast.LENGTH_SHORT).show()
            }
            else{
//                val getUsersTask = GetUsersTask() // Using traditional AsyncTask
//                getUsersTask.execute("https://api.github.com/search/users?q=$username")


                //using okhttp library reduces code drastically and has advantages over traditional async task
                makeNetworkCall("https://api.github.com/search/users?q=$username")

            }
        }
    }

    private fun makeNetworkCall(url : String){
        val client = OkHttpClient()
        val request: Request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.body == null){
                    Log.d(TAG, "onResponse: Failed")
                }
                else {
                    val result = response.body!!.string()
//                    val jsonObject = JSONObject(result)
//                    val items = jsonObject.getJSONArray("items")
                    val gson = Gson() // using Gson library for parsing json objects
                    val apiResult : ApiResult = gson.fromJson(result,ApiResult::class.java)

                    Log.d(TAG, "onResponse: ${apiResult.items}")
                    //listOfUsers= apiResult.items
                    for(i in 0 until apiResult.items.size){
                        listOfUsers.add(apiResult.items[i])
                    }
                    Log.d(TAG, "onResponse: ${listOfUsers}")
                    this@MainActivity.runOnUiThread {
                        //this takes execution from background thread to main thread
                        customAdapter.notifyDataSetChanged()
                    }
                }
            }

        })
    }

//    inner class GetUsersTask : AsyncTask<String, Unit, String>() {
//        override fun doInBackground(vararg params: String?): String {
//            val urlString = params[0]
//
//            val url  = URL(urlString)
//            val httpURLConnection = url.openConnection()
//            val inputStream = httpURLConnection.getInputStream()
//            val scanner = Scanner(inputStream)
//            scanner.useDelimiter("\\A")
//
//            if(scanner.hasNext()){
//                return scanner.next()
//            }
//            return "F"
//        }
//
//        override fun onPostExecute(result: String) {
//            super.onPostExecute(result)
//            val jsonObject = JSONObject(result)
//            val items = jsonObject.getJSONArray("items")
//            for(i in 0 until items.length()){
//                val jObject = items.getJSONObject(i)
//                listOfUsers.add(
//                    User(
//                        jObject.getString("login"),
//                        jObject.getString("url"),
//                        jObject.getInt("id"),
//                        jObject.getDouble("score")
//                    )
//                )
//            }
//            customAdapter.notifyDataSetChanged()
//        }
//    }
}

