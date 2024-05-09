package com.project.pkm_ud_lima.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.pkm_ud_lima.databinding.FragmentCatatanBinding
import com.project.pkm_ud_lima.helper.ViewModelFactory
import com.project.pkm_ud_lima.ui.activity.CatatanAddUpdateActivity

class CatatanFragment : Fragment() {
    private lateinit var binding: FragmentCatatanBinding
    private lateinit var adapter: CatatanAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatatanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val catatanViewModel = obtainViewModel()
        catatanViewModel.getAllCatatan().observe(viewLifecycleOwner) { catatanList ->
            if (catatanList != null) {
                adapter.setListCatatan(catatanList)
            }
        }

        adapter = CatatanAdapter()

        binding.rvNotes.layoutManager = LinearLayoutManager(context)
        binding.rvNotes.setHasFixedSize(true)
        binding.rvNotes.adapter = adapter

        binding.fabAdd.setOnClickListener {
            val intent = Intent(requireActivity(), CatatanAddUpdateActivity::class.java)
            startActivity(intent)
        }
    }

    private fun obtainViewModel(): CatatanViewModel {
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        return ViewModelProvider(this, factory).get(CatatanViewModel::class.java)
    }
}