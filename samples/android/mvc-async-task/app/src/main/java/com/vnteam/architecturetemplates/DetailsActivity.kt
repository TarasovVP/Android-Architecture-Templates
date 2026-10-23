package com.vnteam.architecturetemplates

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.vnteam.architecturetemplates.MainActivity.Companion.DEMO_OBJECT

class DetailsActivity : AppCompatActivity() {

    @BindView(R.id.demoObjectName)
    lateinit var demoObjectName: TextView

    @BindView(R.id.ownerName)
    lateinit var ownerName: TextView

    @BindView(R.id.demoObjectDescription)
    lateinit var demoObjectDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        ButterKnife.bind(this)
        val demoObject = intent.getSerializableExtra(DEMO_OBJECT) as? DemoObject
        demoObjectName.text = demoObject?.fullName
        ownerName.text = demoObject?.owner?.login
        demoObjectDescription.text = demoObject?.fullName
        setOwnerAvatar(demoObject)
    }

    @OnClick(R.id.backButton)
    fun onBackButtonClick() {
        onBackPressed()
    }

    private fun setOwnerAvatar(demoObject: DemoObject?) {
        val ownerAvatar = findViewById<ImageView>(R.id.ownerAvatar)
        val httpClientConnector = OkHttpClientConnector()
        httpClientConnector.getBitmapFromHttpUrlUrl(demoObject?.owner?.avatarUrl, { imageFromUrl ->
            ownerAvatar.setImageBitmap(imageFromUrl)
        }, { errorText ->
            Toast.makeText(this, errorText, Toast.LENGTH_SHORT).show()
        })
    }
}