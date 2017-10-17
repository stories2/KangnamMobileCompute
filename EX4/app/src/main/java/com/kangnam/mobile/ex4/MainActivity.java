package com.kangnam.mobile.ex4;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Locale;

public class MainActivity extends Activity {

    TextToSpeech tts;
    EditText etxtBeforeStt;
    String TAG = "EX4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etxtBeforeStt = (EditText) findViewById(R.id.etxtBeforeStt);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {//초기화 과정이 끝났는지 확인하는 인터페이스이기 때문에
            @Override
            public void onInit(int status) {
                Log.d(TAG, "tts init");
                tts.setLanguage(Locale.KOREAN);//셋팅 해주는건 초기화 후에 해줘야 잘 동작함

                tts.setPitch(2);
                tts.setSpeechRate(0.5f);

                tts.speak("영웅은 죽지 않아요", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    public void OnSttBtnClickListener(View view) {
        Log.d(TAG, "stt button clicked");

        String targetText = etxtBeforeStt.getText().toString();
        tts.speak(targetText, TextToSpeech.QUEUE_ADD, null);//QUEUE_FLUSH : 클린하고 적용, QUEUE_ADD : 기존에 있는거에 추가 적용
    }
}
