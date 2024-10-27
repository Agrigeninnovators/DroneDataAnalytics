package com.example.agis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agis.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding

    // Define questions and answers
    private val qaPairs = listOf(
        "Q.How does the app use drone data for crop monitoring?" to "The app collects and analyzes data from various sensors on drones, like NDVI, NDRE, and thermal sensors, to provide real-time insights into crop health, water stress, and nutrient levels.",
        "Q.What are the benefits of using this app for farm management?" to "It offers precision farming insights, helping farmers manage resources efficiently by providing recommendations for irrigation, fertilizer, and pesticide applications.",
        "Q.Can I access historical crop data?" to "Yes, the app saves historical data to help track crop health over time and make informed decisions based on previous seasons.",
        "Q.How does the AI chatbot work?" to "The chatbot analyzes collected data and provides easy-to-understand insights, allowing non-technical users to implement expert recommendations.",
        "Q.What types of sensors are compatible with the app?" to "The app supports multiple sensors like NDVI, NDRE, GNDVI, NDWI, and thermal, which collectively give a comprehensive view of crop conditions.",
        "Q.Is internet connectivity required for the app to work?" to "The app primarily works offline, but an internet connection may be required for updates or cloud data storage."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView for questions
        val adapter = QuestionAdapter(qaPairs) { answer ->
            binding.chatOutput.text = answer
            binding.chatOutput.background=getDrawable(R.drawable.edit_text_background)
        }
        binding.recyclerViewQuestions.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewQuestions.adapter = adapter
    }
}