package com.rober.dragdemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rober.dragdemo.databinding.ItemPagerBinding

class ViewPagerAdapter(private val context: Context, private val data: MutableList<Bean>) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPagerBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pager, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val url = data[position].url
        Glide.with(context)
            .load(url)
            .centerCrop()
            .into(holder.binding.ivImage)
    }
}