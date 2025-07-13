package com.example.project5

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private val apiKey = "YLAVGKHuPV7fIXaEZuZEreVzzLqHCJJkIJ644bWp" // Replace with your actual NASA API key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val descriptionTextView = findViewById<TextView>(R.id.descriptionTextView)
        val nextButton = findViewById<Button>(R.id.nextButton)

        // Initial fetch for today's APOD
        fetchApod(LocalDate.now().toString(), imageView, titleTextView, descriptionTextView)

        nextButton.setOnClickListener {
            val randomDate = getRandomDate()
            fetchApod(randomDate, imageView, titleTextView, descriptionTextView)
        }
    }

    private fun fetchApod(date: String, imageView: ImageView, titleView: TextView, descView: TextView) {
        RetrofitInstance.api.getApod(apiKey, date).enqueue(object : Callback<ApodResponse> {
            override fun onResponse(call: Call<ApodResponse>, response: Response<ApodResponse>) {
                if (response.isSuccessful) {
                    val apod = response.body()
                    titleView.text = apod?.title ?: "No Title"
                    descView.text = apod?.explanation ?: "No Description"
                    Glide.with(this@MainActivity).load(apod?.url).into(imageView)
                } else {
                    titleView.text = "Error loading data"
                    descView.text = response.message()
                }
            }

            override fun onFailure(call: Call<ApodResponse>, t: Throwable) {
                titleView.text = "Failure"
                descView.text = t.localizedMessage
            }
        })
    }

    private fun getRandomDate(): String {
        val startDate = LocalDate.of(1995, 6, 16)
        val endDate = LocalDate.now()
        val randomEpochDay = (startDate.toEpochDay()..endDate.toEpochDay()).random()
        return LocalDate.ofEpochDay(randomEpochDay).toString()
    }
}

