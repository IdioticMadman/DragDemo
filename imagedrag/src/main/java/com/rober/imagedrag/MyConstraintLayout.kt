package com.rober.imagedrag

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View
import android.view.ViewConfiguration
import androidx.constraintlayout.widget.ConstraintLayout
import com.blankj.utilcode.util.ConvertUtils

class MyConstraintLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    companion object {
        private const val TAG = "MyConstraintLayout"
    }

    var style = MakeImageView.STYLE_TOP
        set(value) {
            field = value
            requestLayout()
        }

    private var mTouchDelegate: UpdatableTouchDelegate? = null
    private lateinit var makeImageView: MakeImageView

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.i(TAG, "dispatchTouchEvent: $ev")
        return super.dispatchTouchEvent(ev)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        makeImageView = findViewById(R.id.iv_gesture)
    }

    private val touchHeightScope = ConvertUtils.dp2px(20f)


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.i(TAG, "left: $left, top:$top, right:$right, bottom:$bottom")

    }

    private val mTemp = IntArray(2)
    private val mTemp2 = IntArray(2)

    private fun getChildBounds(view: View, rect: Rect) {
        view.getLocationInWindow(mTemp) // makeView
        getLocationInWindow(mTemp2) // constraintLayout
        val left: Int = mTemp[0] - mTemp2[0]
        val top: Int = mTemp[1] - mTemp2[1]
        rect.set(left, top, left + view.width, top + view.height)
    }


}