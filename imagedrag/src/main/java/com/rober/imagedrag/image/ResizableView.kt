package com.rober.imagedrag.image

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import com.blankj.utilcode.util.ConvertUtils
import com.rober.imagedrag.MakeImageView
import com.rober.imagedrag.UpdatableTouchDelegate
import kotlin.math.roundToInt

class ResizableView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    companion object {
        private const val TAG = "ResizableView"
        const val STYLE_TOP = 0
        const val STYLE_BOTTOM = 1
    }

    private var thisWidth: Int = 0
    private var thisHeight: Int = 0
    private val touchWidth = ConvertUtils.dp2px(45f).toFloat()
    private val touchHeight = ConvertUtils.dp2px(3f).toFloat()
    private val touchHeightScope = ConvertUtils.dp2px(20f).toFloat()
    private val touchRound = ConvertUtils.dp2px(3f).toFloat()

    private val touchBottomRect = RectF()
    private val touchBottomRectScope = RectF()

    private val touchTopRect = RectF()
    private val touchTopRectScope = RectF()
    private val paint = Paint()
    private val debugPaint = Paint()
    private val viewRect = RectF()

    var style = STYLE_TOP
        set(value) {
            field = value
            requestLayout()
        }

    private val debug = true

    init {
        paint.color = ContextCompat.getColor(context, android.R.color.white)
        paint.strokeWidth = ConvertUtils.dp2px(1f).toFloat()
        paint.style = Paint.Style.STROKE

        debugPaint.color = ContextCompat.getColor(context, android.R.color.holo_red_light)
        debugPaint.style = Paint.Style.FILL
    }

    var onHeightChange: ((Float) -> Unit)? = null

    private val mOverLayoutBoundsExpanded = Rect()
    private val mOverLayoutBounds = Rect()

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        thisWidth = right - left
        thisHeight = bottom - top
        viewRect.set(0f, 0f, thisWidth.toFloat(), thisHeight - touchHeight / 2f)
        touchBottomRect.set(
            (thisWidth - touchWidth) / 2f,
            thisHeight - touchHeight,
            (thisWidth + touchWidth) / 2f,
            thisHeight.toFloat()
        )
        touchBottomRectScope.set(
            (thisWidth - touchWidth) / 2f,
            thisHeight - touchHeightScope,
            (thisWidth + touchWidth) / 2f,
            thisHeight.toFloat() + touchHeightScope
        )

        touchTopRect.set(
            (thisWidth - touchWidth) / 2f,
            0f,
            (thisWidth + touchWidth) / 2f,
            touchHeight
        )
        touchTopRectScope.set(
            (thisWidth - touchWidth) / 2f,
            -touchHeightScope,
            (thisWidth + touchWidth) / 2f,
            touchHeightScope
        )
        mOverLayoutBounds.set(left, top, right, bottom)
        mOverLayoutBoundsExpanded.set(mOverLayoutBounds)
        if (style == MakeImageView.STYLE_TOP) {
            mOverLayoutBoundsExpanded.top = mOverLayoutBounds.top - touchHeightScope.toInt()
        } else if (style == MakeImageView.STYLE_BOTTOM) {
            mOverLayoutBoundsExpanded.bottom = mOverLayoutBounds.bottom + touchHeightScope.toInt()
        }
        Log.i(
            TAG,
            "mOverLayoutBounds:$mOverLayoutBounds, mOverLayoutBoundsExpanded:$mOverLayoutBoundsExpanded"
        )
        if (mTouchDelegate == null) {
            mTouchDelegate = UpdatableTouchDelegate(
                mOverLayoutBoundsExpanded,
                mOverLayoutBounds, this
            )
            (parent as ViewGroup).touchDelegate = mTouchDelegate
        } else {
            mTouchDelegate?.setBounds(mOverLayoutBoundsExpanded, mOverLayoutBounds)
        }
    }

    private var mTouchDelegate: UpdatableTouchDelegate? = null


    var canDrag = false
    var touchDownX = 0f
    var touchDownY = 0f
    var currentHeight = 0

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val result = super.dispatchTouchEvent(ev)
        Log.i(TAG, "dispatchTouchEvent: result:$result, $ev")
        return result
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (canDrag(event)) {
                    canDrag = true
                    touchDownX = event.rawX
                    touchDownY = event.rawY
                    currentHeight = thisHeight
                    Log.i(TAG, "down: $currentHeight, $touchDownY")
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val movedHeight = currentHeight + when (style) {
                    STYLE_TOP -> {
                        touchDownY - event.rawY
                    }
                    STYLE_BOTTOM -> {
                        (event.rawY - touchDownY)
                    }
                    else -> {
                        0f
                    }
                }
                Log.i(TAG, "current:$currentHeight,  movedHeight:$movedHeight")
                onHeightChange?.invoke(movedHeight)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                canDrag = false
                touchDownX = 0f
                touchDownY = 0f
                currentHeight = 0
            }
        }
        Log.i(TAG, "onTouchEvent canDrag:$canDrag,${event.rawX}-${event.rawY}- $event")
        return canDrag
    }

    private fun canDrag(event: MotionEvent): Boolean {
        Log.i(TAG, "canDrag: ${event.x} - ${event.y}")
        return when (style) {
            STYLE_TOP -> {
                touchTopRectScope.contains(event.x, event.y)
            }
            STYLE_BOTTOM -> {
                touchBottomRectScope.contains(event.x, event.y)
            }
            else -> {
                false
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        paint.style = Paint.Style.STROKE
        canvas.drawRect(viewRect, paint)
        if (style == STYLE_BOTTOM) {
            paint.style = Paint.Style.FILL
            if (debug) {
                canvas.drawRect(touchBottomRectScope, debugPaint)
            }
            canvas.drawRoundRect(touchBottomRect, touchRound, touchRound, paint)
        } else if (style == STYLE_TOP) {
            paint.style = Paint.Style.FILL
            if (debug) {
                canvas.drawRect(touchTopRectScope, debugPaint)
            }
            canvas.drawRoundRect(touchTopRect, touchRound, touchRound, paint)
        }
    }
}