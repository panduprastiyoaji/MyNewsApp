package com.example.mynews.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.mynews.R
import com.example.mynews.core.domain.model.News
import com.example.mynews.databinding.ActivityDetailNewsBinding
import com.example.mynews.di.GlobalSingleton
import com.example.mynews.di.GlobalSingletonListener
import org.koin.android.viewmodel.ext.android.viewModel

class DetailNewsActivity : AppCompatActivity() {
    private val detailNewsViewModel: DetailNewsViewModel by viewModel()
    private lateinit var binding: ActivityDetailNewsBinding
    private val listener = Listener()

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val detailNews = intent.getParcelableExtra<News>(EXTRA_DATA)
        showDetailNews(detailNews)
    }

    private fun showDetailNews(detailNews: News?) {
        detailNews?.let {
            supportActionBar?.title = detailNews.title
            binding.content.tvAuthorText.text = detailNews.author
            val dateTime = detailNews.publishedAt
            val date = dateTime.substring(0, 10)
            val time = dateTime.substring(11, 16)
            val formattedDate = "$date $time"
            binding.content.tvPublishText.text = formattedDate
            binding.content.tvContentText.text = detailNews.content
            binding.content.tvDescriptionText.text = detailNews.description
            Glide.with(this@DetailNewsActivity)
                .load(detailNews.urlToImage)
                .into(binding.ivDetailImage)
            var statusFavorite = detailNews.isFavorite
            setStatusFavorite(statusFavorite)
            binding.fab.setOnClickListener {
                statusFavorite = !statusFavorite
                detailNewsViewModel.setFavoriteNews(detailNews, statusFavorite)
                setStatusFavorite(statusFavorite)
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite))
            Toast.makeText(this, "Berhasil Ditambah ke Favorite", Toast.LENGTH_LONG).show()
        } else {
            binding.fab.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_not_favorite_white
                )
            )
            Toast.makeText(this, "Berhasil Dihapus dari Favorite", Toast.LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        super.onStart()
        GlobalSingleton.register(listener)
    }

    override fun onStop() {
        super.onStop()
        GlobalSingleton.unregister(listener)
    }

    private inner class Listener : GlobalSingletonListener {
        override fun onEvent() {}
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}