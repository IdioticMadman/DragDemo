package com.rober.imagedrag

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.rober.imagedrag.image.GestureImageView
import com.tencent.mp.feature.editor.ui.drawable.MakeImageMaskDrawable
import java.io.File


class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private val registerFile: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                Log.i(TAG, "uri:$uri")
                showImage(uri)
            }
        }

    private fun showImage(uri: Uri) {
        val view = findViewById<MakeImageView>(R.id.iv_gesture)
        view.resetCropImageView()
        view.imageView.setImageUri(uri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn_choose).setOnClickListener {
            chooseImage()
        }
        findViewById<View>(R.id.v_test).background = MakeImageMaskDrawable()
    }

    private fun chooseImage() {
        registerFile.launch("image/*")
    }
}