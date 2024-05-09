package com.project.pkm_ud_lima.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.pkm_ud_lima.databinding.FragmentRiwayatBinding

class RiwayatFragment : Fragment() {
    private lateinit var binding: FragmentRiwayatBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { binding = FragmentRiwayatBinding.inflate(inflater, container, false)
        return binding.root
    }
}