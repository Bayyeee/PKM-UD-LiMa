package com.project.pkm_ud_lima.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.project.pkm_ud_lima.R
import com.project.pkm_ud_lima.databinding.ActivityMainBinding
import com.project.pkm_ud_lima.service.FlameService
import com.project.pkm_ud_lima.ui.fragment.CatatanFragment
import com.project.pkm_ud_lima.ui.fragment.HomeFragment
import com.project.pkm_ud_lima.ui.fragment.RiwayatFragment

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    // public getter untuk view binding di dalam activity main
    var binding: ActivityMainBinding
        get() = _binding
        set(value) {
            _binding = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestPermissions()

        // Set padding untuk menghindari overlap dengan status bar dan navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi flame service untuk mendeteksi api dari DB secara continous
        val flameService = Intent(this, FlameService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(flameService)
        } else {
            startService(flameService)
        }

        // Inisialisasi DrawerLayout untuk navigation drawer
        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        drawerLayout = binding.drawerLayout
        val navDrawer: NavigationView = binding.navDrawer

        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navDrawer.setNavigationItemSelectedListener {
            // TODO: handle navigation drawer item clicks
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Inisialisasi BottomNavigationView
        val navView: BottomNavigationView = binding.navView

        // Inisialisasi Fragment
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun requestPermissions() {
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                if (permissions.values.all { it }) {
                    Toast.makeText(this, "Semua izin aplikasi sudah aktif", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Izin aplikasi tidak diberikan", Toast.LENGTH_SHORT).show()
                }
            }

        fun allPermissionGranted(): Boolean {
            val permissions = arrayOf(
                Manifest.permission.POST_NOTIFICATIONS,
            )

            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
            return true
        }

        // Meminta izin aplikasi
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissionLauncher.launch(arrayOf(
                Manifest.permission.POST_NOTIFICATIONS,
            ))
        }
        if (!allPermissionGranted()) {
            Toast.makeText(this, "Izin aplikasi belum diberikan", Toast.LENGTH_SHORT).show()
            return
        }
    }
}
