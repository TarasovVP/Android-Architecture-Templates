package com.vnteam.architecturetemplates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vnteam.architecturetemplates.list.ListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, ListFragment.newInstance())
            addToBackStack(null)
            commit()
        }
    }

    companion object {
        const val DATABASE_NAME = "ArchitectureTemplates"
        const val DEMO_OBJECT_URL = "https://api.github.com/repos/octocat/Spoon-Knife/forks"
    }
}