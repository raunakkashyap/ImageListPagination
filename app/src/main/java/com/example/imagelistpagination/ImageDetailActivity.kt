package com.example.imagelistpagination

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.imagelistpagination.databinding.ActivityImageDetailBinding
import org.koin.android.ext.android.inject

class ImageDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityImageDetailBinding

    private val imageViewModel: ImageListingViewModel by inject()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBarMain.visibility = View.VISIBLE

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Set navigation click listener on toolbar
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // Observe image detail
        imageViewModel.imageDetails.observe(this) {
            binding.progressBarMain.visibility = View.GONE
            Glide.with(this)
                .load(it.downloadUrl)
                .centerCrop()
                .placeholder(R.drawable.placeholder_image)
                .into(binding.imageView)
            binding.authorNameTV.text = it.author
            binding.heightDataTV.text = it.height.toString()
            binding.widthDataTV.text = it.width.toString()
            binding.urlDataTV.text = it.url
            binding.downloadUrlDataTV.text = it.downloadUrl
        }

        binding.urlDataTV.setOnClickListener(this)
        binding.downloadUrlDataTV.setOnClickListener(this)

        // Fetch image detail
        val imageId = intent.getStringExtra("image_id")
        if (imageId != null) {
            imageViewModel.fetchImageDetail(imageId)
        }

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.urlDataTV -> {
                if (binding.urlDataTV.text.toString().isNotEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(binding.urlDataTV.text.toString()))
                    try {
                        startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        // Handle exception
                        Toast.makeText(this, "No browser found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            R.id.downloadUrlDataTV -> {
                val downloadUrl = binding.downloadUrlDataTV.text.toString()
                downloadImage(downloadUrl)
            }
        }
    }

    private fun downloadImage(imageUrl: String) {
        val request = DownloadManager.Request(Uri.parse(imageUrl))
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "image.jpg")
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = downloadManager.enqueue(request)
        Toast.makeText(this, "Downloading image...", Toast.LENGTH_SHORT).show()
    }

}