package com.kangnam.mobile.ex4;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;

import java.util.Locale;

public class MainActivity extends Activity {

    TextToSpeech tts;
    String TAG = "EX4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {//초기화 과정이 끝났는지 확인하는 인터페이스이기 때문에
            @Override
            public void onInit(int status) {
                Log.d(TAG, "tts init");
                tts.setLanguage(Locale.KOREAN);//셋팅 해주는건 초기화 후에 해줘야 잘 동작함
            }
        });
    }

    public void OnSttBtnClickListener(View view) {
        Log.d(TAG, "stt button clicked");
    }
}
