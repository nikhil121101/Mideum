package com.example.mideum.ui


import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mideum.R
import com.google.android.material.button.MaterialButton

@BindingAdapter("setDate")
fun formatDate(textView: TextView, date: String) {
    val shortDate = date.substring(0, 10)
    val engDate = "${shortDate.substring(8, 10)} ${month(shortDate.substring(5, 7))} ${shortDate.substring(0, 4)}"
    textView.text = engDate
}

@BindingAdapter("loadImage")
fun loadImage(iv: ImageView, url: String?) {
    url?.let {
        val uri = url.toUri().buildUpon().scheme("https").build()
        Glide.with(iv.context).load(url)
            .placeholder(R.drawable.loading_animation).error(R.drawable.broken_image)
            .circleCrop()
            .into(iv)
    }
}

@BindingAdapter("loadProfileImage")
fun loadProfileImage(iv: ImageView, url: String?) {
    url?.let {
        val uri = url.toUri().buildUpon().scheme("https").build()

        Glide.with(iv.context).load(url)
                .placeholder(R.drawable.loading_animation).error(R.drawable.broken_image)
                .circleCrop()
                .into(iv)
    }
}

@BindingAdapter("setFavourite")
fun setFavourite(ib : ImageButton , favourite : Boolean) {
    if(favourite) {
        ib.setImageResource(R.drawable.ic_baseline_favorite_24)
    }
    else {
        ib.setImageResource(R.drawable.ic_baseline_favorite_border_24)
    }
}

@BindingAdapter("setFollowButton")
fun setFollowButton(button: MaterialButton, following: Boolean) {
    if(following) {
        button.text = "Unfollow"
        button.setIconResource(R.drawable.ic_baseline_block_24)
    }
    else {
        button.text = "Follow"
        button.setIconResource(R.drawable.ic_baseline_check_24)
    }
}
fun month(s: String): String {
    return when(s) {
        "01" -> "January"
        "02" -> "February"
        "03" -> "March"
        "04" -> "April"
        "05" -> "May"
        "06" -> "June"
        "07" -> "July"
        "08" -> "August"
        "09" -> "September"
        "10" -> "October"
        "11" -> "November"
        "12" -> "December"
        else -> "__"
    }
}