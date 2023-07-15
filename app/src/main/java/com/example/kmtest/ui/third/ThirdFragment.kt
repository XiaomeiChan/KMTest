package com.example.kmtest.ui.third

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kmtest.databinding.FragmentThirdBinding
import com.example.kmtest.model.network.DataItem
import com.example.kmtest.ui.adapter.LoadingStateAdapter
import com.example.kmtest.ui.adapter.UserListAdapter
import com.example.kmtest.ui.second.SecondViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class ThirdFragment : Fragment() {
    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ThirdViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserListAdapter
    private val secondViewModel: SecondViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefresh()

        binding.btnBack.setOnClickListener {
            val toSecond = ThirdFragmentDirections.actionThirdFragmentToSecondFragment()
            findNavController().navigate(toSecond)
        }

    }

    private fun swipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            lifecycleScope.launch {
                viewModel.deleteAll()
            }
            onObserveData()
            binding.swipeRefreshLayout.isRefreshing = false
        }

    }
    private fun onObserveData() {
        adapter = UserListAdapter()
        recyclerView = binding.rvUser
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        binding.listProgressBar.visibility = View.INVISIBLE
        val dividerItemDecoration =
            DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        viewModel.getUsers().observe(viewLifecycleOwner) { pagingData ->
            adapter.submitData(lifecycle, pagingData!!)
            adapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
                override fun onItemClicked(data: DataItem) {
                    adapter.retry()
                    val name = "${data.firstName} ${data.lastName}"

                    showSelectedUser(name)
                }
            })
        }
    }

    private fun showSelectedUser(selected: String) {
        val toDetailStory =
            ThirdFragmentDirections.actionThirdFragmentToSecondFragment()
        secondViewModel.setSelected(selected)
        findNavController().navigate(toDetailStory)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        lifecycleScope.launch {
            viewModel.deleteAll()
        }
    }

    override fun onResume() {
        super.onResume()
        onObserveData()
    }
}