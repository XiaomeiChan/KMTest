package com.example.kmtest.ui.first

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kmtest.databinding.FragmentFirstBinding
import com.example.kmtest.ui.second.SecondViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject


class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding
    private val viewModel: SecondViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvName = binding.inputName.text
        val tvPalindrome = binding.inputPalindrome.text

        binding.btnCheck.setOnClickListener {
            if (isPalindrome(tvPalindrome.toString())) {
                Snackbar.make(requireView(), "It's palindrome", Toast.LENGTH_SHORT).show()
                closeKeyboard(requireView())
            } else {
                Snackbar.make(requireView(), "It's not palindrome", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnNext.setOnClickListener {
            val toSecond =
                FirstFragmentDirections.actionFirstFragmentToSecondFragment()
            viewModel.setName(tvName.toString())
            findNavController().navigate(toSecond)
        }
    }


    private fun isPalindrome(str: String): Boolean {
        val reversed = str.reversed()
        closeKeyboard(requireView())
        return str == reversed
    }

    private fun closeKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}