package com.rober.imagedrag

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.rober.imagedrag.image.GestureImageView
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
        val view = findViewById<GestureImageView>(R.id.iv_gesture)
        view.setImageUri(uri, Uri.fromFile(File(cacheDir, "result.jpg")))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn_choose).setOnClickListener {
            chooseImage()
        }
    }

    private fun chooseImage() {
        registerFile.launch("image/*")
    }
}