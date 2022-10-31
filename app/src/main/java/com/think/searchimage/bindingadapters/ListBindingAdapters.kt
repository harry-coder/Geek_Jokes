package com.think.searchimage.bindingadapters

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.think.searchimage.R

@BindingAdapter("imageAvatar")
 fun imageAvatar(view: ImageView,url:String?){
    if (url != null && url.isNotEmpty() && url.isNotBlank()) {
        Picasso.get()
            .load(url)
            .resize(120,120)
            .centerCrop()

            .into(view)

    } else {
        Picasso.get()
            .load(url)
            .error(R.drawable.ic_launcher_background)
            .into(view)
    }


}

@BindingAdapter("repoName")
fun repoName(view:TextView,name:String) {
    val str = SpannableString(name)
    str.setSpan(
        StyleSpan(Typeface.BOLD),
        name.length,
        str.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    view.text = str.toString().toUpperCase()
}