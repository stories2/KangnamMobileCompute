package com.mobile.kangnam.ex3;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener{

    final String TAG = "EX3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                Log.d(TAG, "Add button clicked");
                break;
            case R.id.btnList:
                Log.d(TAG, "List button clicked");
                break;
            default:
                break;
        }
    }
}
