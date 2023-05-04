package com.rober.dragdemo

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.util.Log
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class CustomItemTouchCallback(private val mItemTouchStatus: ItemTouchStatus) :
    ItemTouchHelper.Callback() {
    companion object {
        private const val TAG = "CustomItemTouchCallback"
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        // 上下拖动
        Log.i(TAG, "getMovementFlags: $viewHolder")
        val dragFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(makeFlag(ItemTouchHelper.ACTION_STATE_DRAG, dragFlags), 0)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        // 交换在数据源中相应数据源的位置
        Log.i(TAG, "onMove: viewHolder:$viewHolder, target:$target")
        return mItemTouchStatus.onItemMove(
            viewHolder.bindingAdapterPosition,
            target.bindingAdapterPosition
        )
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // Nothing
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if (viewHolder != null) {
            Log.i(TAG, "onSelectedChanged: $viewHolder, actionState:$actionState")
            val scaleX = ObjectAnimator.ofFloat(viewHolder.itemView, "scaleX", 1.0f, 1.2f)
            val scaleY = ObjectAnimator.ofFloat(viewHolder.itemView, "scaleY", 1.0f, 1.2f)
            val set = AnimatorSet()
            set.interpolator = DecelerateInterpolator()
            set.setDuration(200)
                .play(scaleX).with(scaleY)
            set.start()
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        val scaleX = ObjectAnimator.ofFloat(viewHolder.itemView, "scaleX", 1.2f, 1.0f)
        val scaleY = ObjectAnimator.ofFloat(viewHolder.itemView, "scaleY", 1.2f, 1.0f)
        val set = AnimatorSet()
        set.interpolator = DecelerateInterpolator()
        set.setDuration(200)
            .play(scaleX).with(scaleY)
        set.start()
        Log.i(TAG, "clearView: $viewHolder")
    }
}