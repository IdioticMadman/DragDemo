package com.rober.imagedrag

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import com.blankj.utilcode.util.ConvertUtils
import com.rober.imagedrag.image.GestureImageView
import com.rober.imagedrag.image.ResizableView

class MakeImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    companion object {
        private const val TAG = "MakeImageView"
        const val STYLE_TOP = 0
        const val STYLE_BOTTOM = 1
    }

    var style = STYLE_TOP
        set(value) {
            field = value
            requestLayout()
        }

    var imageView: GestureImageView
    val overlayView: ResizableView

    init {
        LayoutInflater.from(context).inflate(R.layout.make_image_view, this, true)
        imageView = findViewById(R.id.image_view)
        overlayView = findViewById(R.id.overlay_view)
        overlayView.onHeightChange = { height ->
            if (height > ConvertUtils.dp2px(48f)) {
                updateLayoutParams<ViewGroup.LayoutParams> {
                    this.height = height.toInt()
                }
            }
        }
        imageView.updatePadding(bottom = ConvertUtils.dp2px(1.5f))
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val result = super.dispatchTouchEvent(ev)
        Log.i(TAG, "dispatchTouchEvent: result:$result, $ev")
        return result
    }


    private val touchHeightScope = ConvertUtils.dp2px(20f)

    private var mTouchDelegate: UpdatableTouchDelegate? = null

    private val mMakeViewBoundsExpanded = Rect()
    private val mMakeViewBounds = Rect()

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.i(TAG, "left: $left, top:$top, right:$right, bottom:$bottom")
        mMakeViewBounds.set(left, top, right, bottom)
        mMakeViewBoundsExpanded.set(mMakeViewBounds)
        if (style == STYLE_TOP) {
            mMakeViewBoundsExpanded.top = mMakeViewBounds.top - touchHeightScope
        } else if (style == STYLE_BOTTOM) {
            mMakeViewBoundsExpanded.bottom = mMakeViewBounds.bottom + touchHeightScope
        }
        Log.i(
            TAG,
            "mMakeViewBounds:$mMakeViewBounds, mMakeViewBoundsExpanded:$mMakeViewBoundsExpanded"
        )
        if (mTouchDelegate == null) {
            mTouchDelegate = UpdatableTouchDelegate(
                mMakeViewBoundsExpanded,
                mMakeViewBounds, this
            )
            (parent as ViewGroup).touchDelegate = mTouchDelegate
        } else {
            mTouchDelegate?.setBounds(mMakeViewBoundsExpanded, mMakeViewBounds)
        }
    }

    fun resetCropImageView() {
        removeView(imageView)
        imageView = GestureImageView(context)
        addView(
            imageView, 0, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
    }
}