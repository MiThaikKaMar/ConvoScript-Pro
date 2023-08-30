package com.convoscript.app.activities

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.convoscript.app.R
import com.convoscript.app.databinding.ActivityMainBinding
import com.convoscript.app.helpers.SpeechRecognitionHelper

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    private var speechRecognitionHelper: SpeechRecognitionHelper? = null


    private val REQUEST_RECORD_AUDIO_PERMISSION = 111
    private val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        checkPerm()
        initHelper()
        setUpActionListener()
    }

    private fun setUpActionListener() {
        binding.btnRecognized.setOnClickListener {
            speechRecognitionHelper?.startSpeechRecognition()
        }
    }

    private fun initHelper(){
        speechRecognitionHelper = SpeechRecognitionHelper(this) { transcribedText ->
            // Handle the transcribed text here
            Log.e("Result",transcribedText)
        }
    }

    private fun checkPerm(){
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)
    }


    override fun onDestroy() {
        super.onDestroy()
        speechRecognitionHelper?.destroy()
    }



    // Remember to handle permissions results
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can start speech recognition
            } else {
                // Permission denied, handle accordingly
            }
        }
    }
}