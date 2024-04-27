package com.project.pkm_ud_lima

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.pkm_ud_lima.databinding.FragmentCatatanBinding

class CatatanFragment : Fragment() {
    private lateinit var binding: FragmentCatatanBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { binding = FragmentCatatanBinding.inflate(inflater, container, false)
        return binding.root
    }
}