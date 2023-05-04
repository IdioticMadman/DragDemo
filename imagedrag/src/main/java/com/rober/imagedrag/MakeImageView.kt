package com.rober.imagedrag

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.updateLayoutParams
import com.blankj.utilcode.util.ConvertUtils
import com.rober.imagedrag.image.GestureImageView
import com.rober.imagedrag.image.ResizableView

class MakeImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    var imageView: GestureImageView
    val overlayView: ResizableView

    init {
        LayoutInflater.from(context).inflate(R.layout.make_image_view, this, true)
        imageView = findViewById(R.id.image_view)
        overlayView = findViewById(R.id.overlay_view)
        overlayView.onHeightConfirmed = { height ->
            if (height > ConvertUtils.dp2px(48f)) {
                updateLayoutParams<ViewGroup.LayoutParams> {
                    this.height = height
                }
                true
            } else {
                false
            }
        }
    }

    fun resetCropImageView() {
        removeView(imageView)
        imageView = GestureImageView(context)
        addView(imageView, 0)
    }
}