package com.rober.dragdemo

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.rober.dragdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val pressColor by lazy { ContextCompat.getColor(this, R.color.pressColor) }
    private val backGroundColor by lazy { ContextCompat.getColor(this, R.color.backgroundColor) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val adapter = RecyclerViewAdapter(generateDataList())
//        binding.rv.adapter = adapter
//        binding.rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        binding.rv.addItemDecoration(LinearLayoutItemDecoration())
//        val itemTouchHelper = ItemTouchHelper(CustomItemTouchCallback(adapter))
//        itemTouchHelper.attachToRecyclerView(binding.rv)
//        val color = androidx.core.graphics.ColorUtils.compositeColors(pressColor, backGroundColor)
//        binding.vTop.background = ColorDrawable(color)
//        binding.vBottom.background = ColorDrawable(backGroundColor)
//        binding.vTop.setOnClickListener {
//            SnackBarUtils.with(binding.root)
//                .setBgColor(backGroundColor)
//                .setMessage("我是snack bar 出来的")
//                .setDuration(SnackBarUtils.LENGTH_SHORT)
//                .showWarning(true)
//        }
    }

    private fun generateDataList(): MutableList<Bean> {
        val dataList: MutableList<Bean> = ArrayList()
        dataList.add(Bean("https://cdn.pixabay.com/photo/2013/07/18/20/26/sea-164989_1280.jpg"))
        return dataList
    }
}