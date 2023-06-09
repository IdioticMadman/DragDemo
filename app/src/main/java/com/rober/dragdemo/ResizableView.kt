package com.rober.dragdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet.Motion
import androidx.core.view.updateLayoutParams
import com.blankj.utilcode.util.ConvertUtils
import com.bumptech.glide.Glide.init
import kotlin.math.roundToInt

class ResizableView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        private const val TAG = "ResizableView"
    }

    private var thisWidth: Int = 0
    private var thisHeight: Int = 0
    private val touchWidth = ConvertUtils.dp2px(45f).toFloat()
    private val touchHeight = ConvertUtils.dp2px(3f).toFloat()
    private val touchHeightScope = ConvertUtils.dp2px(20f).toFloat()
    private val touchRound = ConvertUtils.dp2px(3f).toFloat()

    private val touchRect = RectF()
    private val touchRectScope = RectF()
    private val paint = Paint()
    private val viewRect = RectF()

    init {
        paint.color = resources.getColor(android.R.color.white)
        paint.strokeWidth = ConvertUtils.dp2px(1.0f).toFloat()
        paint.style = Paint.Style.STROKE
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        thisWidth = right - left
        thisHeight = bottom - top
        viewRect.set(0f, 0f, thisWidth.toFloat(), thisHeight - touchHeight / 2f)
        touchRect.set(
            (thisWidth - touchWidth) / 2f,
            thisHeight - touchHeight,
            (thisWidth + touchWidth) / 2f,
            thisHeight.toFloat()
        )
        touchRectScope.set(
            (thisWidth - touchWidth) / 2f,
            thisHeight - touchHeightScope,
            (thisWidth + touchWidth) / 2f,
            thisHeight.toFloat()
        )
    }

    var canDrag = false
    var touchDownX = 0f
    var touchDownY = 0f
    var currentHeight = 0

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (canDrag(event)) {
                    canDrag = true
                    touchDownX = event.x
                    touchDownY = event.y
                    currentHeight = thisHeight
                }
            }
            MotionEvent.ACTION_MOVE -> {
                updateLayoutParams<ViewGroup.LayoutParams> {
                    height = currentHeight + (event.y - touchDownY).roundToInt()
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                canDrag = false
                touchDownX = 0f
                touchDownY = 0f
                currentHeight = 0
            }
        }
        Log.i(TAG, "canDrag:$canDrag, touch event: $event")
        return canDrag
    }

    private fun canDrag(event: MotionEvent): Boolean {
        return touchRectScope.contains(event.x, event.y)
    }

    override fun onDraw(canvas: Canvas) {
        paint.style = Paint.Style.STROKE
        canvas.drawRect(viewRect, paint)
        paint.style = Paint.Style.FILL
        canvas.drawRoundRect(touchRect, touchRound, touchRound, paint)
    }
}