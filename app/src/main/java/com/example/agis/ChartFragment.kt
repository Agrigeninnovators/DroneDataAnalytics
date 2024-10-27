package com.example.agis

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import java.text.SimpleDateFormat
import java.util.Locale
import android.view.ViewGroup
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.Entry
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.agis.ChatAIMainActivity;
import com.example.agis.ui.theme.AGISTheme as AGISTheme1

class ChartFragment : Fragment() {

    private lateinit var lineChart: LineChart
    private lateinit var db: FirebaseFirestore

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chart, container, false)
        lineChart = view.findViewById(R.id.lineChart)
        FirebaseApp.initializeApp(this@ChartFragment.requireContext())
        // Initialize Firestore
        db = Firebase.firestore

        // Set up the chart
        setupChart()
        val chatButton: FloatingActionButton = view.findViewById(R.id.AiActionButton)
        chatButton.setOnClickListener {
                   Intent(this@ChartFragment.requireContext(), ChatAIMainActivity::class.java)
                }

        return view
    }

    private fun setupChart() {
        // Define the date format of your document IDs
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // Retrieve data from Firestore
        db.collection("sensor_data")
            .get()
            .addOnSuccessListener { result ->
                val waterUsageEntries = mutableListOf<Entry>()
                val fertilizerUsageEntries = mutableListOf<Entry>()
                val nutrientUsageEntries = mutableListOf<Entry>()

                // Loop through the documents
                for (document in result) {
                    val dateString = document.id
                    val data = document.data

                    // Parse the document ID as a date
                    val date = try {
                        dateFormat.parse(dateString)?.time?.toFloat() ?: continue
                    } catch (e: Exception) {
                        e.printStackTrace()
                        continue
                    }

                    // Extract sensor values from the document
                    val ndvi = (data["NDVI"] as? Number)?.toFloat() ?: 0f
                    val gndvi = (data["GNDVI"] as? Number)?.toFloat() ?: 0f
                    val ndre = (data["NDRE"] as? Number)?.toFloat() ?: 0f
                    val ndwi = (data["NDWI"] as? Number)?.toFloat() ?: 0f

                    // Add entries to the lists
                    waterUsageEntries.add(Entry(date, ndvi))
                    fertilizerUsageEntries.add(Entry(date, gndvi))
                    nutrientUsageEntries.add(Entry(date, ndre))
                }

                // Create LineDataSet for each type
                val waterUsageDataSet = LineDataSet(waterUsageEntries, "NDVI")
                waterUsageDataSet.color = resources.getColor(R.color.blue) // Set color
                waterUsageDataSet.valueTextColor = resources.getColor(R.color.blue)

                val fertilizerUsageDataSet = LineDataSet(fertilizerUsageEntries, "GNDVI")
                fertilizerUsageDataSet.color = resources.getColor(R.color.green)
                fertilizerUsageDataSet.valueTextColor = resources.getColor(R.color.green)

                val nutrientUsageDataSet = LineDataSet(nutrientUsageEntries, "NDRE")
                nutrientUsageDataSet.color = resources.getColor(R.color.red)
                nutrientUsageDataSet.valueTextColor = resources.getColor(R.color.red)

                // Combine data sets into LineData
                val lineData = LineData(waterUsageDataSet, fertilizerUsageDataSet, nutrientUsageDataSet)

                // Set data to the chart
                lineChart.data = lineData
                lineChart.invalidate() // Refresh the chart
            }
            .addOnFailureListener { exception ->
                // Handle any errors
                exception.printStackTrace()
            }
    }
}