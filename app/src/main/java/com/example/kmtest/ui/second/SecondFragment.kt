package com.example.kmtest.ui.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kmtest.R
import com.example.kmtest.databinding.FragmentSecondBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    private val viewModel: SecondViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.name.observe(viewLifecycleOwner) { name ->
            binding.tvName.text = name

        }
        viewModel.selected.observe(viewLifecycleOwner) { selected ->
            if (selected == null) {
                binding.tvChoosen.text = requireContext().resources.getString(R.string.tv_selected)
            } else {
                binding.tvChoosen.text = selected
            }

        }
        binding.btnChoose.setOnClickListener {
            val toThird = SecondFragmentDirections.actionSecondFragmentToThirdFragment()
            findNavController().navigate(toThird)
        }

        binding.btnBack.setOnClickListener {
            val toFirst = SecondFragmentDirections.actionSecondFragmentToFirstFragment()
            findNavController().navigate(toFirst)
        }

    }
}