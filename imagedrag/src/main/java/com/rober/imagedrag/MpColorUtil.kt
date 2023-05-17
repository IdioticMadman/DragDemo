package com.rober.imagedrag

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.annotation.Size
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 *
 * @author fredguo
 * @since 2020/12/22
 */

object MpColorUtil {
    val code = arrayOf(
        "0",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "A",
        "B",
        "C",
        "D",
        "E",
        "F"
    )

    fun distanceOf(@Size(3) hsl1: FloatArray, @Size(3) hsl2: FloatArray): Double {
        val x1 = hsl1[1] * cos(hsl1[0] * 180.0f / PI)
        val x2 = hsl2[1] * cos(hsl2[0] * 180.0f / PI)
        val y1 = hsl1[1] * sin(hsl1[0] * 180.0f / PI)
        val y2 = hsl2[1] * sin(hsl2[0] * 180.0f / PI)
        val z1 = hsl1[2]
        val z2 = hsl2[2]
        val diffX = x1 - x2
        val diffY = y1 - y2
        val diffZ = z1 - z2
        return sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ)
    }


    /**
     * Set the alpha component of `color` to be `alpha`.
     *
     * @param color The color.
     * @param alpha Alpha component \([0..255]\) of the color.
     * @return the `color` with `alpha` component
     */
    fun setAlphaComponent(
        @ColorInt color: Int,
        @IntRange(from = 0x0, to = 0xFF) alpha: Int
    ): Int {
        return color and 0x00ffffff or (alpha shl 24)
    }

    /**
     * Set the alpha component of `color` to be `alpha`.
     *
     * @param color The color.
     * @param alpha Alpha component \([0..1]\) of the color.
     * @return the `color` with `alpha` component
     */
    fun setAlphaComponent(
        @ColorInt color: Int,
        @FloatRange(from = 0.0, to = 1.0) alpha: Float
    ): Int {
        return color and 0x00ffffff or ((alpha * 255.0f + 0.5f).toInt() shl 24)
    }

    /**
     * Set the red component of `color` to be `red`.
     *
     * @param color The color.
     * @param red   Red component \([0..255]\) of the color.
     * @return the `color` with `red` component
     */
    fun setRedComponent(
        @ColorInt color: Int,
        @IntRange(from = 0x0, to = 0xFF) red: Int
    ): Int {
        return color and -0xff0001 or (red shl 16)
    }

    /**
     * Set the red component of `color` to be `red`.
     *
     * @param color The color.
     * @param red   Red component \([0..1]\) of the color.
     * @return the `color` with `red` component
     */
    fun setRedComponent(
        @ColorInt color: Int,
        @FloatRange(from = 0.0, to = 1.0) red: Float
    ): Int {
        return color and -0xff0001 or ((red * 255.0f + 0.5f).toInt() shl 16)
    }

    /**
     * Set the green component of `color` to be `green`.
     *
     * @param color The color.
     * @param green Green component \([0..255]\) of the color.
     * @return the `color` with `green` component
     */
    fun setGreenComponent(
        @ColorInt color: Int,
        @IntRange(from = 0x0, to = 0xFF) green: Int
    ): Int {
        return color and -0xff01 or (green shl 8)
    }

    /**
     * Set the green component of `color` to be `green`.
     *
     * @param color The color.
     * @param green Green component \([0..1]\) of the color.
     * @return the `color` with `green` component
     */
    fun setGreenComponent(
        @ColorInt color: Int,
        @FloatRange(from = 0.0, to = 1.0) green: Float
    ): Int {
        return color and -0xff01 or ((green * 255.0f + 0.5f).toInt() shl 8)
    }

    /**
     * Set the blue component of `color` to be `blue`.
     *
     * @param color The color.
     * @param blue  Blue component \([0..255]\) of the color.
     * @return the `color` with `blue` component
     */
    fun setBlueComponent(
        @ColorInt color: Int,
        @IntRange(from = 0x0, to = 0xFF) blue: Int
    ): Int {
        return color and -0x100 or blue
    }

    /**
     * Set the blue component of `color` to be `blue`.
     *
     * @param color The color.
     * @param blue  Blue component \([0..1]\) of the color.
     * @return the `color` with `blue` component
     */
    fun setBlueComponent(
        @ColorInt color: Int,
        @FloatRange(from = 0.0, to = 1.0) blue: Float
    ): Int {
        return color and -0x100 or (blue * 255.0f + 0.5f).toInt()
    }

    /**
     * Color-string to color-int.
     *
     * Supported formats are:
     *
     *
     *  * `#RRGGBB`
     *  * `#AARRGGBB`
     *
     *
     *
     * The following names are also accepted: `red`, `blue`,
     * `green`, `black`, `white`, `gray`,
     * `cyan`, `magenta`, `yellow`, `lightgray`,
     * `darkgray`, `grey`, `lightgrey`, `darkgrey`,
     * `aqua`, `fuchsia`, `lime`, `maroon`,
     * `navy`, `olive`, `purple`, `silver`,
     * and `teal`.
     *
     * @param colorString The color-string.
     * @return color-int
     * @throws IllegalArgumentException The string cannot be parsed.
     */
    fun string2Int(colorString: String): Int {
        return Color.parseColor(colorString)
    }

    /**
     * Color的Int整型转Color的16进制颜色值【方案二】
     * colorInt - -12590395
     * return Color的16进制颜色值——#3FE2C5
     */
    fun int2Hex(colorInt: Int, withAlpha: Boolean = true): String {
        val rgb = if (withAlpha) int2Argb(colorInt) else int2Rgb(colorInt)
        return rgb2Hex(rgb)
    }

    /**
     * Color的Int整型转Color的rgb数组
     * colorInt - -12590395
     * return Color的rgb数组 —— [63,226,197]
     */
    fun int2Rgb(colorInt: Int): IntArray {
        val rgb = intArrayOf( 0, 0, 0)
        val red = Color.red(colorInt)
        val green = Color.green(colorInt)
        val blue = Color.blue(colorInt)
        rgb[0] = red
        rgb[1] = green
        rgb[2] = blue
        return rgb
    }

    /**
     * Color的Int整型转Color的rgb数组
     * colorInt - -12590395
     * return Color的rgb数组 —— [63,226,197]
     */
    fun int2Argb(colorInt: Int): IntArray {
        val rgb = intArrayOf(0, 0, 0, 0)
        val alpha = Color.alpha(colorInt)
        val red = Color.red(colorInt)
        val green = Color.green(colorInt)
        val blue = Color.blue(colorInt)
        rgb[0] = alpha
        rgb[1] = red
        rgb[2] = green
        rgb[3] = blue
        return rgb
    }

    /**
     * rgb数组转Color的16进制颜色值
     * rgb - rgb数组——[63,226,197]
     * return Color的16进制颜色值——#3FE2C5
     */
    fun rgb2Hex(rgb: IntArray): String {
        val hexCode = StringBuilder("#")
        for (j in rgb) {
            var item = j
            if (item < 0) {
                item = 0
            } else if (item > 255) {
                item = 255
            }
            val lCode = item / 16 //先获取商，例如，255 / 16 == 15
            val rCode = item % 16 //再获取余数，例如，255 % 16 == 15
            hexCode.append(code[lCode]).append(code[rCode]) //FF
        }
        return hexCode.toString()
    }

    fun getRandomColor(): Int {
        return getRandomColor(true)
    }

    fun getRandomColor(supportAlpha: Boolean): Int {
        val high = if (supportAlpha) (Math.random() * 0x100).toInt() shl 24 else -0x1000000
        return high or (Math.random() * 0x1000000).toInt()
    }

    /**
     * Return whether the color is light.
     *
     * @param color The color.
     * @return `true`: yes<br></br>`false`: no
     */
    fun isLightColor(@ColorInt color: Int): Boolean {
        return 0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color) >= 127.5
    }

}

