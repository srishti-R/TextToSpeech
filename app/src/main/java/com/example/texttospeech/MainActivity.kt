package com.example.texttospeech

import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var tts: TextToSpeech
    var textToSpeak: String = ""
    var pos = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tts = TextToSpeech(this, this)
        for (engines in tts.engines) {
            Log.d("Engine Info ", engines.toString())
        }
        for (locale in Locale.getAvailableLocales()) {
            Log.d(
                "LOCALES",
                locale.language
                    .toString() + "_" + locale.country + " [" + locale.displayName + "]"
            )
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                pos = p2
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        btn_speak.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textToSpeak = resources.getStringArray(R.array.greetings)[pos]
                val result = when (pos) {
                    1 -> tts.setLanguage(Locale("hin", "IND")) //hindi
                    2 -> tts.setLanguage(Locale("bn", "IND")) //bengali
                    3 -> tts.setLanguage(Locale("gu", "IND")) //gujrati
                    4 -> tts.setLanguage(Locale("mr", "IND")) //marathi
                    5 -> tts.setLanguage(Locale("pa", "IND")) //punjabi
                    6 -> tts.setLanguage(Locale("kn", "IND")) //kannada
                    7 -> tts.setLanguage(Locale("ta", "IND")) //tamil
                    8 -> tts.setLanguage(Locale("te", "IND")) //telugu
                    9 -> tts.setLanguage(Locale("or", "IND")) //odia
                    10 -> tts.setLanguage(Locale("sd", "IND")) //sindhi
                    11 -> tts.setLanguage(Locale("ml", "IND")) //malyalam
                    12 -> tts.setLanguage(Locale("ne", "IND")) //nepali
                    13 -> tts.setLanguage(Locale("ur", "IND")) //urdu
                    14 -> tts.setLanguage(Locale.CHINESE) //chinese simplified
                    else -> tts.setLanguage(Locale.US) //english
                }
                if (result == TextToSpeech.LANG_MISSING_DATA) {
                    Log.e("TTS", "The Language specified is missing!")
                }
                if (result == TextToSpeech.LANG_NOT_SUPPORTED) Log.e(
                    "TTS",
                    "language not supported"
                )
                tts.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, "")
            }
        }
    }

    override fun onInit(p0: Int) {
        if (p0 == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            } else {
                btn_speak.isEnabled = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Log.d("lang", tts.availableLanguages.toString())
                }
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }

    }

    override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}