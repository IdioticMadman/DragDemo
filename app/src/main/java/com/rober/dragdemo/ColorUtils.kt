package com.rober.dragdemo

import java.util.*

object ColorUtils {
    fun generateRandomColor(): String {
        //红色
        var red: String
        //绿色
        var green: String
        //蓝色
        var blue: String
        //生成随机对象
        val random = Random()
        //生成红色颜色代码
        red = Integer.toHexString(random.nextInt(256)).uppercase(Locale.getDefault())
        //生成绿色颜色代码
        green = Integer.toHexString(random.nextInt(256)).uppercase(Locale.getDefault())
        //生成蓝色颜色代码
        blue = Integer.toHexString(random.nextInt(256)).uppercase(Locale.getDefault())

        //判断红色代码的位数
        red = if (red.length == 1) "0$red" else red
        //判断绿色代码的位数
        green = if (green.length == 1) "0$green" else green
        //判断蓝色代码的位数
        blue = if (blue.length == 1) "0$blue" else blue
        //生成十六进制颜色值
        return "#$red$green$blue"
    }
}