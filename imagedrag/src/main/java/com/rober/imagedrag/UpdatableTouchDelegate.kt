package com.rober.imagedrag

import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View
import kotlin.math.roundToInt

class UpdatableTouchDelegate(
    targetBounds: Rect,
    actualBounds: Rect,
    delegateView: View
) : TouchDelegate(targetBounds, delegateView) {
    companion object {
        private const val TAG = "UpdatableTouchDelegate"
    }

    /**
     * View that should receive forwarded touch events
     */
    private val mDelegateView: View

    /**
     * Bounds in local coordinates of the containing view that should be mapped to the delegate
     * view. This rect is used for initial hit testing.
     */
    private val mTargetBounds: Rect

    /**
     * Bounds in local coordinates of the containing view that are actual bounds of the delegate
     * view. This rect is used for event coordinate mapping.
     */
    private val mActualBounds: Rect

    /**
     * True if the delegate had been targeted on a down event (intersected mTargetBounds).
     */
    private var mDelegateTargeted = false

    init {
        mTargetBounds = Rect()
        mActualBounds = Rect()
        setBounds(targetBounds, actualBounds)
        mDelegateView = delegateView
    }

    fun setBounds(desiredBounds: Rect, actualBounds: Rect) {
        mTargetBounds.set(desiredBounds)
        mActualBounds.set(actualBounds)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x.roundToInt()
        val y = event.y.roundToInt()
        var sendToDelegate = false
        var handled = false
        when (event.action) {
            MotionEvent.ACTION_DOWN -> if (mTargetBounds.contains(x, y)) {
                mDelegateTargeted = true
                sendToDelegate = true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_MOVE -> {
                sendToDelegate = mDelegateTargeted
            }
            MotionEvent.ACTION_CANCEL -> {
                sendToDelegate = mDelegateTargeted
                mDelegateTargeted = false
            }
        }
        if (sendToDelegate) {
            // Offset event coordinates to the target view coordinates.
            event.offsetLocation(-mActualBounds.left.toFloat(), -mActualBounds.top.toFloat())
            handled = mDelegateView.dispatchTouchEvent(event)
        }
        Log.i(TAG, "handled: $handled")
        return handled
    }
}