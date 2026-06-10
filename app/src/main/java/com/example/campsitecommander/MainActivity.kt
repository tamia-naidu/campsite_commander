package com.example.campsitecommander

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    companion object {
        val itemArray = ArrayList<String>()
        val categoryArray = ArrayList<String>()
        val quantityArray = ArrayList<Int>()
        val commentsArray = ArrayList<String>()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // System edge-to-edge padding implementation
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val edtItem = findViewById<EditText>(R.id.editItem)
        val edtCategory = findViewById<EditText>(R.id.editCategory)
        val edtQuantity = findViewById<EditText>(R.id.editQuantity)
        val edtComments = findViewById<EditText>(R.id.editComments)
        val txtTotalItems = findViewById<TextView>(R.id.txtTotalItems)
        
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnView = findViewById<Button>(R.id.btnView)
        val btnLoadEssentials = findViewById<Button>(R.id.btnLoadEssentials)

        updateTotalDisplay(txtTotalItems)

        btnLoadEssentials.setOnClickListener {
            loadSampleData()
            updateTotalDisplay(txtTotalItems)
            Toast.makeText(this, "Essential camping gear loaded!", Toast.LENGTH_SHORT).show()
        }

        btnAdd.setOnClickListener {
            val item = edtItem.text.toString().trim()
            val category = edtCategory.text.toString().trim()
            val quantityText = edtQuantity.text.toString().trim()
            val comments = edtComments.text.toString().trim()

            if (item.isEmpty() || category.isEmpty() || quantityText.isEmpty() || comments.isEmpty()) {
                Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    val quantity = quantityText.toInt()
                    itemArray.add(item)
                    categoryArray.add(category)
                    quantityArray.add(quantity)
                    commentsArray.add(comments)

                    val total = updateTotalDisplay(txtTotalItems)

                    Toast.makeText(
                        this,
                        "Item Added! Total items packed: $total",
                        Toast.LENGTH_SHORT
                    ).show()

                    clearFields(edtItem, edtCategory, edtQuantity, edtComments)
                } catch (_: NumberFormatException) {
                    Toast.makeText(this, "Quantity must be a number", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnView.setOnClickListener {
            startActivity(Intent(this, DetailedViewActivity::class.java))
        }
    }

    private fun loadSampleData() {
        val samples = listOf(
            Triple("Tent", "Shelter", 1) to "4-person waterproof tent",
            Triple("Sleeping Bag", "Shelter", 4) to "For the lowest expected temperature",
            Triple("Flashlight/Headlamp", "Safety", 1) to "Bring extra batteries",
            Triple("First Aid Kit", "Safety", 1) to "Bandages, gauze, and antiseptic",
            Triple("Cookware Set", "Cooking", 1) to "Skillet, pot, spatula",
            Triple("Cooler & Ice", "Cooking", 2) to "Keeps perishable food cold",
            Triple("Soap & Sponge", "Sanitation", 4) to "Leave no trace",
            Triple("Trash Bags", "Sanitation", 2) to "Always pack out trash",
            Triple("Camping chairs", "Comfort", 2) to "For relaxing around the fire",
            Triple("Insect Repellent", "Comfort", 1) to "Keeps mosquitoes away"
        )

        for (sample in samples) {
            itemArray.add(sample.first.first)
            categoryArray.add(sample.first.second)
            quantityArray.add(sample.first.third)
            commentsArray.add(sample.second)
        }
    }

    private fun updateTotalDisplay(textView: TextView): Int {
        var totalItemsPacked = 0
        for (q in quantityArray) {
            totalItemsPacked += q
        }
        textView.text = "Total Items Packed: $totalItemsPacked"
        return totalItemsPacked
    }

    private fun clearFields(
        item: EditText,
        category: EditText,
        quantity: EditText,
        comments: EditText
    ) {
        item.text.clear()
        category.text.clear()
        quantity.text.clear()
        comments.text.clear()
    }
}
