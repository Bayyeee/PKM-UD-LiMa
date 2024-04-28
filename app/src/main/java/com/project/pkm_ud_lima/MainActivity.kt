package com.project.pkm_ud_lima

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.pkm_ud_lima.databinding.ActivityMainBinding
import com.project.pkm_ud_lima.fragment.CatatanFragment
import com.project.pkm_ud_lima.fragment.HomeFragment
import com.project.pkm_ud_lima.fragment.RiwayatFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi BottomNavigationView
        val navView: BottomNavigationView = binding.navView

        val homeFragment = HomeFragment()
        val catatanFragment = CatatanFragment()
        val riwayatFragment = RiwayatFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment_activity_main, homeFragment)
            commit()
        }

        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_home -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.nav_host_fragment_activity_main, homeFragment)
                        commit()
                    }
                }
                R.id.item_catatan -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.nav_host_fragment_activity_main, catatanFragment)
                        commit()
                    }
                }
                R.id.item_riwayat -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.nav_host_fragment_activity_main, riwayatFragment)
                        commit()
                    }
                }
            }
            true
        }
    }
}
