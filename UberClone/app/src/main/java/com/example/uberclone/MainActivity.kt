package com.example.uberclone

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {
    var getStartedButton: Button? = null
    var switch: SwitchMaterial? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getStartedButton = findViewById(R.id.getStartedButton)
        switch = findViewById(R.id.switch1)
        getStartedButton!!.setOnClickListener {
            val intent: Intent
            if (switch!!.isChecked) {
                intent = Intent(this, DriverActivity::class.java)
                startActivity(intent)
            } else {
                intent = Intent(this, RiderActivity::class.java)
                startActivity(intent)
            }
        }
        supportActionBar?.hide()
    }
}