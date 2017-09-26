package com.mobile.kangnam.ex2;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {

    public static final String TAG = "test";

    EditText etxtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etxtResult = (EditText)findViewById(R.id.etxtResult);
    }

    public void onClickHttp(View view) {
        Log.d(TAG, "onclick http");
/*
        HttpTask httpTask = new HttpTask();
        httpTask.execute("http://www.google.co.kr");*/

        new HttpTask().execute("http://www.google.co.kr");
    }

    public void onClickWeather(View view) {
        new HttpTask(new JsonParser() {
            @Override
            public void ConvertJsonWeather(String jsonData) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonData);
                    Log.d(TAG, "Result code: " + jsonObject.getString("cod"));

                    etxtResult.setText("Result code: " + jsonObject.getString("cod"));
                }
                catch (Exception err) {
                    Log.e(TAG, "Error: " + err.getMessage());
                }
            }
        }).execute("http://api.openweathermap.org/data/2.5/weather?lat=37.276101&lon=127.130824&APPID=70c844fdcfeb46b7f41aa7b47278e97e");
    }

    interface JsonParser {
        public void ConvertJsonWeather(String jsonData) ;
    }

    class HttpTask extends AsyncTask<String, Void, String> {

        JsonParser jsonParser;

        public HttpTask() {
            super();
        }
        public HttpTask(JsonParser jsonParser) {
            super();
            this.jsonParser = jsonParser;
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "do in background url: " + strings[0]);

            try {
                URL url = new URL(strings[0]);

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
                Log.d(TAG, "Error in doinbackground: " + err.getMessage());
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d(TAG, "background result: " + s);

            if(jsonParser != null) {
                jsonParser.ConvertJsonWeather(s);
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
