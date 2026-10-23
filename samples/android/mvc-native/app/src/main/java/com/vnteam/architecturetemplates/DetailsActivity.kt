package com.vnteam.architecturetemplates

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ccom.vnteam.architecturetemplates.R
import com.vnteam.architecturetemplates.MainActivity.Companion.ERROR
import com.vnteam.architecturetemplates.MainActivity.Companion.DEMO_OBJECT
import com.vnteam.architecturetemplates.MainActivity.Companion.SUCCESS_IMAGE_FROM_URL_CONNECTION

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val demoObject = intent.getSerializableExtra(DEMO_OBJECT) as? DemoObject
        findViewById<TextView>(R.id.demoObjectName).text = demoObject?.full_name
        findViewById<TextView>(R.id.ownerName).text = demoObject?.owner?.login
        findViewById<TextView>(R.id.demoObjectDescription).text = demoObject?.full_name
        setOwnerAvatar(demoObject)
        findViewById<Button>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }
    }

    private fun setOwnerAvatar(demoObject: DemoObject?) {
        val ownerAvatar = findViewById<ImageView>(R.id.ownerAvatar)
        val handler = Handler(Looper.getMainLooper()) { message ->
            when (message.what) {
                SUCCESS_IMAGE_FROM_URL_CONNECTION -> {
                    val imageFromUrl = message.obj as? Bitmap
                    ownerAvatar.setImageBitmap(imageFromUrl)
                }
                ERROR -> {
                    val errorText = message.obj as String
                    Toast.makeText(this, errorText, Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
        val httpUrlConnector = HttpUrlConnector()
        httpUrlConnector.getBitmapFromHttpUrlUrl(demoObject?.owner?.avatar_url, handler)
    }


}