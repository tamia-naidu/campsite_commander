package com.example.campsitecommander

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailedViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detailed_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val density = v.context.resources.displayMetrics.density
            val padding16 = (16 * density).toInt()
            v.setPadding(
                systemBars.left + padding16,
                systemBars.top + padding16,
                systemBars.right + padding16,
                systemBars.bottom + padding16
            )
            insets
        }

        val btnShowGear = findViewById<Button>(R.id.btnShowGear)
        val txtGearDetails = findViewById<TextView>(R.id.txtGearDetails)
        val btnBackToBase = findViewById<Button>(R.id.btnBackToBase)

        btnShowGear.setOnClickListener {
            val sb = StringBuilder()
            for (i in MainActivity.itemArray.indices) {
                sb.append("----------------------------\n")
                sb.append("Item: ${MainActivity.itemArray[i]}\n")
                sb.append("Category: ${MainActivity.categoryArray[i]}\n")
                sb.append("Quantity: ${MainActivity.quantityArray[i]}\n")
                sb.append("Comments: ${MainActivity.commentsArray[i]}\n")
            }
            if (MainActivity.itemArray.isEmpty()) {
                txtGearDetails.text = "No gear added yet."
            } else {
                txtGearDetails.text = sb.toString()
            }
        }

        btnBackToBase.setOnClickListener {
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
