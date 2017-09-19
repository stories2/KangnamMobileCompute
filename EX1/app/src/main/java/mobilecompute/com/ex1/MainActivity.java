package mobilecompute.com.ex1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import static mobilecompute.com.ex1.DefineManager.LOG_LEVEL_ERROR;
import static mobilecompute.com.ex1.DefineManager.LOG_LEVEL_INFO;
import static mobilecompute.com.ex1.LogManager.PrintLog;

public class MainActivity extends Activity implements View.OnClickListener {

    EditText etMemo;
    ImageView ivIcon;
    Button button, button2, button3, button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMemo = (EditText)findViewById(R.id.editText);
        ivIcon = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrintLog("MainActivity", "onCreate", "button click type 1", LOG_LEVEL_INFO);
            }
        });

        ButtonClick clicker = new ButtonClick();

        button2.setOnClickListener(clicker);

        button4.setOnClickListener(this);
    }

    public void btnHttpClick(View v) {
        String c = "http://www.google.co.kr";
        new HttpTask().execute(c);
    }

    public void btnWeatherClick(View v) {
        String c = "http://api.openweathermap.org/data/2.5/weather?lat=37.276101&lon=127.130824&APPID=8ff8987";

        new HttpTask().execute(c);
    }

    public void btnParsingClick(View v) {
        try {
            String str = etMemo.getText().toString();

            JSONObject jObj = new JSONObject(str);
            JSONObject cord = jObj.getJSONObject("coord");
            JSONObject main = jObj.getJSONObject("main");

            etMemo.setText("");
            etMemo.append(cord.getString("lon") + "\n");
            etMemo.append(main.getString("humidity") + "\n");
            etMemo.append(main.getString("temp") + "\n");

            JSONArray weathers = jObj.getJSONArray("weather");
            JSONObject weather = weathers.getJSONObject(0);
            etMemo.append(weather.getString("main") + "\n");

            String icon = weather.getString("icon");

            int resID = getResources().getIdentifier("i02d", "drawable", getPackageName());
            ivIcon.setImageDrawable(getResources().getDrawable(resID));
        }
        catch (Exception err) {

        }
    }

    public void btnAttendClick(View v) {
        String c = "code=mobilecomputing&type=begin&";
        c = "http://i2max-ml.xyz/MobileCompute/ex1.php?" + c + "phone=01032214331&api=qwerty12345";

        new HttpTask(new OnCompletionListener() {
            @Override
            public void onComplete(String result) {
                try {
                    PrintLog("MainActivity", "btnAttendClick/onComplete", "result: " + result, LOG_LEVEL_INFO);
                    JSONObject jObj;

                    jObj = new JSONObject(result);

                    JSONObject response = jObj.getJSONObject("response");
                    String tmp = response.getString("msg") + "  " + response.getString("phone");
                    Toast.makeText(getApplicationContext(), tmp, Toast.LENGTH_SHORT).show();
                }
                catch (Exception err) {
                    PrintLog("MainActivity", "btnAttendClick/onComplete", "Error: " + err.getMessage(), LOG_LEVEL_ERROR);
                }
            }

            @Override
            public void onComplete(ApiResponse result) {

            }
        }, etMemo).execute(c);
        HttpTask2 httpTask2 = new HttpTask2(new OnCompletionListener() {
            @Override
            public void onComplete(String result) {
            }

            @Override
            public void onComplete(ApiResponse result) {
                String resultResponse = "";
                ApiRecursiveResponse recursiveResponse = result.getResponse();
                Toast.makeText(getApplicationContext(), recursiveResponse.getMsg(), Toast.LENGTH_SHORT).show();

                resultResponse = "msg: " + recursiveResponse.getMsg()
                        + " phone: " + recursiveResponse.getPhone();

                PrintLog("MainActivity", "btnAttendClick/onComplete", resultResponse, LOG_LEVEL_INFO);

                etMemo.setText(resultResponse);
            }
        });

        httpTask2.RequestOpenApi("mobilecomputing", "begin", "01056351845", "qwerty12345");
    }

    @Override
    public void onClick(View view) {
        PrintLog("MainActivity", "onClick", "button press type 4", LOG_LEVEL_INFO);

        new Thread() {
            public void run() {
                int i;
                for(i = 0; i < 100000000; i += 1) {

                }
                PrintLog("MainActivity", "onClick/run", "process done", LOG_LEVEL_INFO);
            }
        }.start();
        PrintLog("MainActivity", "onClick", "button press type 4 end", LOG_LEVEL_INFO);
    }

    class ButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            PrintLog("ButtonClick", "onClick", "onclick type 2", LOG_LEVEL_INFO);
        }
    }

    interface OnCompletionListener {
        void onComplete(String result);
        void onComplete(ApiResponse result);
    }
}
