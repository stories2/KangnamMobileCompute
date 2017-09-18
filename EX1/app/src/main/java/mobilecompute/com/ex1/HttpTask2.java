package mobilecompute.com.ex1;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static mobilecompute.com.ex1.DefineManager.LOG_LEVEL_ERROR;
import static mobilecompute.com.ex1.DefineManager.LOG_LEVEL_INFO;
import static mobilecompute.com.ex1.LogManager.PrintLog;

/**
 * Created by stories2 on 2017. 9. 18..
 */

public class HttpTask2 {

    MainActivity.OnCompletionListener listener;

    public HttpTask2() {

    }

    public HttpTask2(MainActivity.OnCompletionListener listener) {
        this.listener = listener;
    }

    public void RequestOpenApi(String code, String type, String phone, String api) {
        RetrofitRouter retrofitRouter = RetrofitRouter.retrofit.create(RetrofitRouter.class);
        Call<ApiResponse> call = retrofitRouter.OpenApiTest(code, type, phone, api);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                try {
                    PrintLog("HttpTask2", "RequestOpenApi/onResponse", "result: " + response.body().toString(), LOG_LEVEL_INFO);
                    listener.onComplete(response.body());
                }
                catch (Exception err) {
                    PrintLog("HttpTask2", "RequestOpenApi/onResponse", "Error: " + err.getMessage(), LOG_LEVEL_ERROR);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                PrintLog("HttpTask2", "RequestOpenApi/onFailure", "Error: " + t.getMessage(), LOG_LEVEL_ERROR);
            }
        });
    }
}