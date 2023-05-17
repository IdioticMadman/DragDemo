package com.tencent.mp.feature.editor.ui.drawable

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.animation.LinearInterpolator
import com.rober.imagedrag.MpColorUtil

// 文字生成图片大背景
class MakeImageMaskDrawable : Drawable() {

    private var color: Int = Color.BLACK
    private var toAlpha = 0
    private var fromAlpha = 255

    companion object {
        private const val TAG = "MakeImage"
    }

    // 背景的画笔
    private val paint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
        }
    }

    init {
        updateShader()
    }

    // 背景渐变
    lateinit var bgLinearGradient: LinearGradient

    private var animator: ValueAnimator? = null

    private val argbEvaluator by lazy { ArgbEvaluator() }

    fun changeToColor(color: Int, fromAlpha: Int, toAlpha: Int) {
        val curColor = this.color
        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 200
            interpolator = LinearInterpolator()
            addUpdateListener {
                val fraction = it.animatedValue as Float
                setColor(
                    argbEvaluator.evaluate(fraction, curColor, color) as Int,
                    fromAlpha,
                    toAlpha
                )
            }
        }
        animator?.start()
    }

    fun setColor(color: Int, fromAlpha: Int, targetAlpha: Int) {
        this.color = color
        this.fromAlpha = fromAlpha
        this.toAlpha = targetAlpha
        updateShader()
        invalidateSelf()
    }

    override fun onBoundsChange(bounds: Rect) {
        Log.v(TAG, "onBoundsChange: bounds")
        bounds ?: return
        updateShader()
    }

    override fun draw(canvas: Canvas) {
        val rect = bounds
        Log.v(TAG, "bounds :$bounds")
        paint.shader = bgLinearGradient
        canvas.drawRect(rect, paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
        invalidateSelf()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("PixelFormat.TRANSLUCENT", "android.graphics.PixelFormat")
    )
    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }


    private fun updateShader() {
        val rect = bounds
        bgLinearGradient = LinearGradient(
            0f,
            0f,
            0f,
            rect.bottom.toFloat(),
            MpColorUtil.setAlphaComponent(color, fromAlpha),
            MpColorUtil.setAlphaComponent(color, toAlpha),
            Shader.TileMode.CLAMP
        )
    }

    fun cancel() {
        animator?.cancel()
    }
}