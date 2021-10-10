package com.think.searchimage.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.think.searchimage.R
import com.think.searchimage.databinding.ItemMediaBinding
import com.think.searchimage.model.FeaturedImages
import java.util.*
import kotlin.collections.ArrayList


class MediaAdapter(
    var context: Context
) : RecyclerView.Adapter<MediaAdapter.ItemViewHolder>() {

    var list: ArrayList<FeaturedImages.Photos.Photo> = arrayListOf()
    var monthList = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaAdapter.ItemViewHolder {

      return  inflateRecentViews(parent)


    }

    private fun inflateRecentViews(parent: ViewGroup): MediaAdapter.ItemViewHolder {
        val binding: ItemMediaBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.item_media, parent, false
        )
        return ItemViewHolder(binding)

    }


    override fun onBindViewHolder(viewHolder: MediaAdapter.ItemViewHolder, position: Int) {
       // https://farm1.static.flickr.com/578/23451156376_8983a8ebc7.jpg
        val item=list[position]
        val mediaUrl= "https://farm${item.farm}.static.flickr.com/${item.server}/${item.id}_${item.secret}.jpg"
        val request: ImageRequest =
            ImageRequestBuilder.newBuilderWithSource(Uri.parse(mediaUrl?: ""))
                .setProgressiveRenderingEnabled(true).build()
        viewHolder.binding.ivMediaThumb.controller = Fresco.newDraweeControllerBuilder()
            .setImageRequest(request)
            .setAutoPlayAnimations(false)
            .build()


    }



    override fun getItemCount(): Int =
        if (list.isNotEmpty()) list.size
        else 0


     fun setSource( imageList:List<FeaturedImages.Photos.Photo>){
        list.addAll(imageList)
         notifyDataSetChanged()
    }

    inner class ItemViewHolder(val binding: ItemMediaBinding) :
        RecyclerView.ViewHolder(binding.root)


}






