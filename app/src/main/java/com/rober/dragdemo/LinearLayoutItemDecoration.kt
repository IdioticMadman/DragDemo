package com.rober.dragdemo

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class LinearLayoutItemDecoration : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val adapterPosition = parent.getChildViewHolder(view).bindingAdapterPosition
        if (adapterPosition == parent.adapter!!.itemCount - 1) {
            outRect.set(DEFAULT_OFFSET, 0, DEFAULT_OFFSET, 0)
        } else {
            outRect.set(DEFAULT_OFFSET, 0, 0, 0)
        }

    }

    companion object {
        private const val DEFAULT_OFFSET = 10
    }
}