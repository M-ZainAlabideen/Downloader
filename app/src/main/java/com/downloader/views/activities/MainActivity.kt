package com.downloader.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.downloader.R
import com.downloader.classes.Navigator
import com.downloader.views.fragments.FilesFragment
import dagger.hilt.android.AndroidEntryPoint


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Navigator.loadFragment(this, FilesFragment.newInstance(this), R.id.fragment_container, true)
    }
}