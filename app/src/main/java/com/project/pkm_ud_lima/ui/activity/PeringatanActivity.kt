package com.project.pkm_ud_lima.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project.pkm_ud_lima.data.response.FlameResponse
import com.project.pkm_ud_lima.data.retrofit.ApiConfig
import com.project.pkm_ud_lima.databinding.ActivityPeringatanBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PeringatanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPeringatanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeringatanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiService = ApiConfig.getFlameService()

        apiService.getFlame().enqueue(object : Callback<FlameResponse> {
            override fun onResponse(call: Call<FlameResponse>, response: Response<FlameResponse>) {
                if (response.isSuccessful) {
                    val flameResponse = response.body()
                    if (flameResponse != null) {
                        val value = flameResponse.valueApi
                        val jarak = flameResponse.jarakApi
                        binding.tvValue.text = value.toString()
                        binding.tvJarak.text = jarak.toString()
                    }
                } else {
                    Toast.makeText(this@PeringatanActivity, "Data sensor tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<FlameResponse>, t: Throwable) {
                Toast.makeText(this@PeringatanActivity, "Tidak terhubung ke sensor", Toast.LENGTH_SHORT).show()
            }
        })
    }
}