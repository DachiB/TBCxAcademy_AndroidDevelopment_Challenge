package com.example.challenge.util

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.example.challenge.R


fun AppCompatImageView.loadImage(url: String?) {
    Glide.with(context)
        .load(url)
        .error(R.drawable.ic_launcher_background)
        .placeholder(R.drawable.ic_launcher_background)
        .into(this)
}