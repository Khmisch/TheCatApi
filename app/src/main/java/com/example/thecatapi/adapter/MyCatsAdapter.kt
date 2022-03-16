package com.example.thecatapi.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thecatapi.R
import com.example.thecatapi.fragment.AllCatsFragment
import com.example.thecatapi.fragment.MyCatsFragment
import com.example.thecatapi.model.Photo
import com.example.thecatapi.model.Post
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class MyCatsAdapter  (var context: MyCatsFragment, var items: ArrayList<Post>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    @SuppressLint("NotifyDataSetChanged")
    fun addPhotos(items: ArrayList<Post>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_view, parent, false)
        return PinsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val photoItem = items[position]
        val photoUrl = photoItem.url!!
        val photoColor = R.color.grey

        if (holder is PinsViewHolder) {
//                holder.tv_description.text = getDescription(s1, s2, s3)
//                Glide.with(context).load(photoUrl)
//                    .placeholder(ColorDrawable(Color.parseColor(photoColor)))
//                    .error(ColorDrawable(Color.parseColor(photoColor)))
//                    .into(holder.iv_pin);
            Picasso.get().load(photoUrl).placeholder(R.color.grey)
                .into(holder.iv_pin)
        }
    }


    class PinsViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var iv_pin: ShapeableImageView = view.findViewById(R.id.iv_pin)
        var tv_description: TextView = view.findViewById(R.id.tv_description)
    }

    private fun getDescription(s1: Any?, s2: String?, s3: String?): String {
        return when {
            s1 != null -> s1.toString()
            s2 != null -> s2.toString()
            else -> "Photo was made by $s3"
        }
    }
}