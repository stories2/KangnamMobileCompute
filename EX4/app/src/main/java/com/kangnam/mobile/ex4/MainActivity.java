package com.kangnam.mobile.ex4;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.net.URLEncoder;
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

    public void OnCallBtnClickListener(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:8282"));
        startActivity(intent);
    }

    public void OnWebBtnClickListener(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.i2max-ml.xyz"));
        startActivity(intent);
    }

    public void OnMapBtnClickListener(View view) {
        String place = "";
        try {
            place = URLEncoder.encode("강남대학교" , "UTF-8" );
            Log.d(TAG, "map: " + place);
        }
        catch (Exception err) {
            Log.d(TAG, "Error in OnMapBtnClickListener: " + err.getMessage());
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.co.kr/maps/place/" + place));
        startActivity(intent);
    }
}
