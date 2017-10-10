package com.mobile.kangnam.ex3;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
                OnClickList();
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
                try {
                    URL url = new URL(params[0]);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");

                    InputStream inputStream = httpURLConnection.getInputStream();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String htmlSource = "", temp = "";
                    while((temp = bufferedReader.readLine()) != null) {
                        htmlSource = htmlSource + temp;
                    }
                    return htmlSource;
                }
                catch (Exception err) {
                    Log.d(TAG, "Error in doinBackground: " + err.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s == null) {
                    Log.d(TAG, "Response is null");
                    return;
                }
                try {
                    Log.d(TAG, "Response data: " + s);
                    JSONObject jsonObject = new JSONObject(s);
                    etxtResult.setText(jsonObject.getString("msg"));
                }
                catch (Exception err) {
                    Log.d(TAG, "Error in on post execute: " + err.getMessage());
                }
            }
        }.execute("http://i2max-ml.xyz:8080/openapi.jsp?method=add&name=" + etxtName.getText().toString());
    }

    public void OnClickList() {
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                Log.d(TAG, "getting data url: " + params[0]);
                try {
                    URL url = new URL(params[0]);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");

                    InputStream inputStream = httpURLConnection.getInputStream();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String htmlSource = "", temp = "";
                    while((temp = bufferedReader.readLine()) != null) {
                        htmlSource = htmlSource + temp;
                    }
                    return htmlSource;
                }
                catch (Exception err) {
                    Log.d(TAG, "Error in doinBackground: " + err.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s == null) {
                    Log.d(TAG, "Response is null");
                    return;
                }
                try {
                    Log.d(TAG, "Response data: " + s);
                    JSONObject jsonObject = new JSONObject(s);
                    etxtResult.setText(jsonObject.getString("msg") + "\n");
                    JSONArray infoArray = jsonObject.getJSONArray("info");
                    int i;
                    for(i = 0; i < infoArray.length(); i += 1) {
                        if(infoArray.getString(i) != null) {
                            etxtResult.append(infoArray.getString(i) + "\n");
                        }
                    }
                }
                catch (Exception err) {
                    Log.d(TAG, "Error in on post execute: " + err.getMessage());
                }
            }
        }.execute("http://i2max-ml.xyz:8080/openapi.jsp?method=list");
    }
}
