package com.mobile.kangnam.ex2;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

        HttpTask httpTask = new HttpTask();
        httpTask.execute("http://www.google.co.kr");
    }

    class HttpTask extends AsyncTask<String, Void, String> {

        public HttpTask() {
            super();
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "do in background url: " + strings[0]);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
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
