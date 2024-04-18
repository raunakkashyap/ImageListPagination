package com.example.imagelistpagination

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagelistpagination.adapter.ImageAdapter
import com.example.imagelistpagination.databinding.ActivityMainBinding
import com.example.imagelistpagination.model.ImageListResponse
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val imageViewModel: ImageListingViewModel by inject()

    private lateinit var adapter: ImageAdapter

    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.progressBarMain.visibility = View.VISIBLE

        adapter =  ImageAdapter { image ->
            // Handle item click here
            // For example, you can show a toast with the author's name
            val intent = Intent(this@MainActivity, ImageDetailActivity::class.java)
            intent.putExtra("image_id", image.id) // Pass any necessary data to ImageDetailActivity
            startActivity(intent)
        }

        binding.imageListRV.layoutManager = LinearLayoutManager(this)
        binding.imageListRV.adapter = adapter

        observeViewModel()

        // Implement pagination logic
        binding.imageListRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount
                if (!isLoading && lastVisibleItemPosition == totalItemCount - 1) {
                    // Reached end of list, load more data
                    imageViewModel.loadNextPage()
                    isLoading = true
                }
            }
        })

    }

    private fun observeViewModel() {
        imageViewModel.imageList.observe(this) { imageList ->
            updateAdapter(imageList)
            isLoading = false
        }
    }

    private fun updateAdapter(imageList: List<ImageListResponse>) {
        adapter.setData(imageList)
        binding.progressBarMain.visibility = View.GONE
    }
}

