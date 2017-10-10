package com.mobile.kangnam.ex3;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements View.OnClickListener{

    final String TAG = "EX3";

    EditText etxtName, etxtResult;
    Button btnAdd, btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
    }

    public void Init() {
        etxtName = (EditText) findViewById(R.id.etxtName);
        etxtResult = (EditText)findViewById(R.id.etxtResult);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnList = (Button)findViewById(R.id.btnList);

        btnAdd.setOnClickListener(this);
        btnList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                Log.d(TAG, "Add button clicked");
                OnClickAdd();
                break;
            case R.id.btnList:
                Log.d(TAG, "List button clicked");
                break;
            default:
                break;
        }
    }

    public void OnClickAdd() {
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                Log.d(TAG, "getting data url: " + params[0]);
                return "Ok";
            }
        }.execute("http://172.16.11.71:8080");
    }
}
