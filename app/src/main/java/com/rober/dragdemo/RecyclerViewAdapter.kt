package com.rober.dragdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rober.dragdemo.RecyclerViewAdapter.RecyclerViewHolder
import com.rober.dragdemo.databinding.ItemRecyclerviewBinding
import java.util.*

class RecyclerViewAdapter(private val mDataList: MutableList<Bean>) :
    RecyclerView.Adapter<RecyclerViewHolder>(), ItemTouchStatus {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.binding.textView.text = mDataList[position].url
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(mDataList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemRemove(position: Int): Boolean {
        mDataList.removeAt(position)
        notifyItemRemoved(position)
        return true
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemRecyclerviewBinding = ItemRecyclerviewBinding.bind(itemView)
    }
}