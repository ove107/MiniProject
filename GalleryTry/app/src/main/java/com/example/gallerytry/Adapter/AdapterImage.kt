package com.example.gallerytry.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.gallerytry.View.Category.ImageFullScreenFragment
import com.example.gallerytry.Model.ImageModel
import com.example.gallerytry.R
import com.squareup.picasso.Picasso

class AdapterImage(val mContext: Context?,val listener: ImageCallBackListener) : RecyclerView.Adapter<AdapterImage.ImageHolder>() {
    private lateinit var mImageList: List<ImageModel>
    class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cImage : ImageView = itemView.findViewById(R.id.catImages)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val v: View = LayoutInflater.from(mContext).inflate(R.layout.image,parent,false)
        return ImageHolder(v)
    }

    override fun getItemCount(): Int {
        //Log.i("Imagelist size",imageList.size.toString())
        return mImageList.size

    }

    fun listChange(images: List<ImageModel>){
        mImageList = images
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        Picasso.get().load(mImageList[position].downloadURL).into(holder.cImage)
        holder.itemView.setOnClickListener{
            val image= ImageModel(mImageList[position].downloadURL
           ,mImageList[position].Timestamp
               ,mImageList[position].CategoryName)
            listener.onClickImage(image)
        }
    }
}