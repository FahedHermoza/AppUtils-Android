package com.fahed.developer.permissions.view.activities.List

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fahed.developer.permissions.R
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

    }

}
