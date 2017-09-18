package mobilecompute.com.ex1;

import android.os.AsyncTask;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static mobilecompute.com.ex1.DefineManager.LOG_LEVEL_ERROR;
import static mobilecompute.com.ex1.DefineManager.LOG_LEVEL_INFO;
import static mobilecompute.com.ex1.LogManager.PrintLog;

/**
 * Created by stories2 on 2017. 9. 18..
 */

public class HttpTask extends AsyncTask<String, Void, String> {

    MainActivity.OnCompletionListener listener;
    EditText etMemo;

    public HttpTask() {
        super();
    }

    public HttpTask(MainActivity.OnCompletionListener listener, EditText etMemo) {
        super();
        this.listener = listener;
        this.etMemo = etMemo;
    }

    @Override
    protected String doInBackground(String... strings) {
        String line = "";
        try {
            URL url = new URL(strings[0]);
            PrintLog("HttpTask", "doInBackground", "request url: " + strings[0], LOG_LEVEL_INFO);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            InputStream is = conn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            while(true) {
                String temp = reader.readLine();
                if(temp != null) {
                    line = line + line;
                }
                else {
                    break;
                }
            }
        }
        catch (Exception err) {
            line = err.getMessage().toString();
            PrintLog("HttpTask", "doInBackground", "Error: " + err.getMessage(), LOG_LEVEL_ERROR);
        }
        return line;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(listener != null) {
            listener.onComplete(s);
            etMemo.setText(s);
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
