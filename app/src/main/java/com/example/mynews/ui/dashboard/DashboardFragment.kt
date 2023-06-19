package com.example.mynews.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mynews.R
import com.example.mynews.core.data.Resources
import com.example.mynews.core.ui.ListNewsAdapter
import com.example.mynews.databinding.FragmentDashboardBinding
import com.example.mynews.di.GlobalSingleton
import com.example.mynews.di.GlobalSingletonListener
import com.example.mynews.ui.detail.DetailNewsActivity
import org.koin.android.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {
    private val dashboardViewModel: DashboardViewModel by viewModel()
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private var query: String = ""
    private var newsAdapter = ListNewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bannerSearch.edSearch.setText("")
        binding.bannerSearch.searchButton.setOnClickListener {
            query = binding.bannerSearch.edSearch.text.toString()
            dashboardViewModel.search(query)
        }
        newsAdapter = ListNewsAdapter()
        newsAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailNewsActivity::class.java)
            intent.putExtra(DetailNewsActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }
        binding.viewError.root.visibility = View.VISIBLE
        binding.viewError.tvError.text = getString(R.string.no_data)
        dashboardViewModel.listNews.observe(viewLifecycleOwner) { listNews ->
            if (listNews != null) {
                when (listNews) {
                    is Resources.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.viewError.root.visibility = View.GONE
                    }
                    is Resources.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.GONE
                        newsAdapter.setData(listNews.data)
                    }
                    is Resources.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text =
                            listNews.message ?: getString(R.string.something_wrong)
                    }
                }
            } else {
                binding.progressBar.visibility = View.GONE
                binding.viewError.root.visibility = View.VISIBLE
                binding.viewError.tvError.text = getString(R.string.something_wrong)
            }
        }
        with(binding.rvNews) {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}