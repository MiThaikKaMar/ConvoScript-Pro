package com.convoscript.app.helpers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import java.util.Locale

class SpeechRecognitionHelper(
    private val context: Context,
    private val callback: (String) -> Unit
) : RecognitionListener {

    private val speechRecognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)

    init {
        speechRecognizer.setRecognitionListener(this)
    }

    fun startSpeechRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
       // intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something...")
        speechRecognizer.startListening(intent)
    }

    fun stopSpeechRecognition() {
        speechRecognizer.stopListening()
    }

    fun cancelSpeechRecognition() {
        speechRecognizer.cancel()
    }

    fun destroy() {
        speechRecognizer.destroy()
    }

    // Implementation of RecognitionListener methods
    override fun onResults(results: Bundle?) {
        val resultList = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
       
        if (resultList != null && resultList.isNotEmpty()) {
            val transcribedText = resultList[0]
            callback(transcribedText)
        }
    }


    // Implement other RecognitionListener methods as needed
    override fun onPartialResults(p0: Bundle?) {
        
    }

    override fun onEvent(p0: Int, p1: Bundle?) {
        
    }


    override fun onReadyForSpeech(p0: Bundle?) {
        // This method is called when the recognizer is ready for the user to start speaking
        // You can provide a prompt to indicate that speech recognition is ready
        Toast.makeText(context, "Speak now...", Toast.LENGTH_SHORT).show()

    }

    override fun onBeginningOfSpeech() {
        
    }

    override fun onRmsChanged(p0: Float) {
        
    }

    override fun onBufferReceived(p0: ByteArray?) {
        
    }

    override fun onEndOfSpeech() {
        
    }

    override fun onError(p0: Int) {
        
    }
}

