package com.example.gallerytry

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gallerytry.R
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class AdapterTimeline(var timelineList: List<addToTimeline>, var mContext: Context?) : RecyclerView.Adapter<AdapterTimeline.MyViewHolder>() {
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var time = itemView.findViewById<TextView>(R.id.timelineTime)
        var image = itemView.findViewById<ImageView>(R.id.timelineImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.timeline_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return timelineList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        timelineList[position].url.addOnSuccessListener {
            Picasso.get().load(it).into(holder.image)
        }
        val formatter = SimpleDateFormat("MMM dd,yyyy")
        val timeDD = formatter.format(Date(timelineList[position].timestamp))
        holder.time.setText(timeDD)
    }

}