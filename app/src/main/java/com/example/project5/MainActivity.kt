package com.example.project5

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project5.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ApodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ApodAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        fetchApods()
    }

    private fun fetchApods() {
        RetrofitInstance.api.getApod(BuildConfig.NASA_API_KEY, 10)
            .enqueue(
            object : Callback<List<ApodResponse>> {
                override fun onResponse(
                    call: Call<List<ApodResponse>>,
                    response: Response<List<ApodResponse>>
                ) {
                    if (response.isSuccessful) {
                        adapter.update(response.body() ?: emptyList())
                    } else {
                        Toast.makeText(this@MainActivity,
                            "API error ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<ApodResponse>>, t: Throwable) {
                    Toast.makeText(this@MainActivity,
                        "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}



