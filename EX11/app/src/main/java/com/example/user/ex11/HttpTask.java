package com.example.user.ex11;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by User on 2017-12-05.
 */

public class HttpTask extends AsyncTask<String , Void , String> {

    public interface OnCompletionListener {
        void onComplete(String result);
    }

    OnCompletionListener listener = null;
    public HttpTask() {         }

    public HttpTask(OnCompletionListener listener) {             this.listener = listener;         }

    protected String doInBackground(String... params)         {
        String line = "";
        try {
            URL Url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF8"));
            int i=0;
            while ( true) {
                String tmp = reader.readLine();
                if ( tmp == null ) break;
                line += tmp + "\n";
                i++;
                if ( i > 40 ) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            line = e.toString();
        }
        return line;
    }

    protected void onPostExecute(String result)         {
        if ( listener != null) listener.onComplete(result);
    }
}