package com.project.pkm_ud_lima.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.pkm_ud_lima.R
import com.project.pkm_ud_lima.data.retrofit.ApiConfig
import com.project.pkm_ud_lima.data.response.CuacaResponse
import com.project.pkm_ud_lima.databinding.FragmentHomeBinding
import com.project.pkm_ud_lima.ui.activity.MainActivity
import com.project.pkm_ud_lima.ui.activity.PeringatanActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    // HashMap translasi cuaca dari bahasa Inggris ke bahasa Indonesia

    private val weatherMap = hashMapOf(
        "Thunderstorm" to "Badai Petir",
        "Drizzle" to "Gerimis",
        "Rain" to "Hujan",
        "Snow" to "Salju",
        "Mist" to "Kabut",
        "Smoke" to "Asap",
        "Haze" to "Kabut",
        "Dust" to "Debu",
        "Fog" to "Kabut",
        "Sand" to "Pasir",
        "Dust" to "Debu",
        "Ash" to "Abu",
        "Squall" to "Angin Kencang",
        "Tornado" to "Puting Beliung",
        "Clear" to "Cerah",
        "Clouds" to "Berawan"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RETROFIT CUACA

        val apiService = ApiConfig.getWeatherService()
        // daerah Rantau Badauh
        val lat = -3.101074
        val lon = 114.717258

        // Ambil data API dan masukkan ke viewbinding

        apiService.getForecast(lat, lon).enqueue(object : Callback<CuacaResponse> {
            override fun onResponse(call: Call<CuacaResponse>, response: Response<CuacaResponse>) {
                if (response.isSuccessful) {
                    val cuacaResponse = response.body()
                    var weather = cuacaResponse?.list?.get(0)?.weather?.get(0)?.main
                    val temperature = cuacaResponse?.list?.get(0)?.main?.temp?.let { Math.round((it as Double)).toInt() }
                    val date = cuacaResponse?.list?.get(0)?.dtTxt

                    val oldFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    val newFormat = SimpleDateFormat("d MMMM yyyy", Locale("in", "ID"))
                    val dayFormat = SimpleDateFormat("EEEE", Locale("in", "ID"))

                    val parsedDate = oldFormat.parse(date)
                    val formattedDate = newFormat.format(parsedDate)
                    val dayDate = dayFormat.format(parsedDate)

                    weather = weatherMap[weather] ?: weather

                    binding.tvCuacahari.text = "$dayDate\n$formattedDate\n$weather"
                    binding.tvCuacasuhu.text = "$temperature"
                    binding.tvSuhusmallcardviewcuaca1.text = "$temperature"
                    binding.tvSuhusmallcardviewcuaca2.text = "$temperature"
                    binding.tvSuhusmallcardviewcuaca3.text = "$temperature"
                    binding.tvSuhusmallcardviewcuaca4.text = "$temperature"

                } else {
                    // Apabila gagal mengambil data cuaca
                    Toast.makeText(context, "Gagal mengambil data cuaca", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CuacaResponse>, t: Throwable) {
                // Apabila koneksi ke API cuaca gagal
                Toast.makeText(context, "Koneksi ke server gagal", Toast.LENGTH_SHORT).show()
            }
        })

        // Click listener CardView Catatan
        val mainActivity = activity as MainActivity
        val bottomNavView: BottomNavigationView = mainActivity.binding.navView
        val cardCatatan: CardView = binding.CardCatatan
        cardCatatan.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_main, CatatanFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()

            bottomNavView.selectedItemId = R.id.item_catatan
        }

        val cardPeringatan: CardView = binding.CardPeringatan
        cardPeringatan.setOnClickListener{
            val intent = Intent(context, PeringatanActivity::class.java)
            startActivity(intent)
        }
    }
}