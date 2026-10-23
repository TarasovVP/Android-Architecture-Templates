package com.vnteam.architecturetemplates

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import ccom.vnteam.architecturetemplates.R

class MainActivity : AppCompatActivity() {

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val sqLiteDBConnector = SQLiteDBConnector(this)
        val listView = findViewById<ListView>(R.id.listView)
        handler = Handler(Looper.getMainLooper()) { message ->
            when (message.what) {
                SUCCESS_HTTP_CONNECTION -> {
                    val responseData = message.obj as String
                    val jsonConverter = JsonConverter()
                    val demoObject = jsonConverter.getDemoObjectList(responseData)

                    sqLiteDBConnector.insertData(handler, demoObject)
                    Log.e("apiTAG", "MainActivity SUCCESS_HTTPS_CONNECTION demoObject $demoObject")
                }
                SUCCESS_SQLITE_CONNECTION -> {
                    val demoObjectList = message.obj as ArrayList<DemoObject>
                    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, demoObjectList.map { it.name })
                    listView.adapter = adapter
                    progressBar.isVisible = false
                    listView.setOnItemClickListener { _, _, position, _ ->
                        Log.e("apiTAG", "MainActivity SUCCESS_SQLITE_CONNECTION setOnItemClickListener position $position")
                        val intent = Intent(this, DetailsActivity::class.java)
                        intent.putExtra(DEMO_OBJECT, demoObjectList[position])
                        startActivity(intent)
                    }
                    Log.e("apiTAG", "MainActivity SUCCESS_SQLITE_CONNECTION demoObjectNameList.size ${demoObjectList.size}")
                }
                ERROR -> {
                    progressBar.isVisible = false
                    val errorText = message.obj as String
                    Toast.makeText(this, errorText, Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

        val startButton = findViewById<Button>(R.id.startButton)
        val httpUrlConnector = HttpUrlConnector()
        startButton.setOnClickListener {
            progressBar.isVisible = true
            httpUrlConnector.makeHttpUrlConnection(handler)
        }
    }

    companion object {
        const val SUCCESS_HTTP_CONNECTION = 1
        const val SUCCESS_SQLITE_CONNECTION = 2
        const val ERROR = 3
        const val SUCCESS_IMAGE_FROM_URL_CONNECTION = 4
        const val DATABASE_NAME = "CleanArchitectureDemo"
        const val TABLE_NAME = "demoObjects"
        const val DEMO_OBJECT = "demoObject"
    }
}