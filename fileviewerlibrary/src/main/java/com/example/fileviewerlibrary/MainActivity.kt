package com.example.fileviewerlibrary

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.helper.widget.Carousel
import androidx.transition.Transition
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget

import com.example.fileviewerlibrary.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var listFile : ArrayList<File>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listFile = intent.getSerializableExtra("data") as ArrayList<File>?
        setupSlider()
    }

    private fun setupSlider() {

        binding.slider.setAdapter(object : Carousel.Adapter {

            override fun count(): Int = listFile!!.size

            override fun populate(view: View, index: Int) {
                val imageView = (view as? ViewGroup)?.getChildAt(0)
                if (imageView is AppCompatImageView) {
                    Glide.with(view)
                        .asBitmap()
                        .load(listFile!![index].absolutePath)
                        .into(createCustomTarget(imageView))
                }
            }

            override fun onNewItem(index: Int) = Unit
        })
    }

    private fun createCustomTarget(view: AppCompatImageView) = object : CustomTarget<Bitmap?>() {
        override fun onResourceReady(resource: Bitmap, transition: com.bumptech.glide.request.transition.Transition<in Bitmap?>?) {
            view.setImageBitmap(resource)
        }
        override fun onLoadCleared(placeholder: Drawable?) = Unit
    }
}