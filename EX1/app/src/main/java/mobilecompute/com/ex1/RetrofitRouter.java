package mobilecompute.com.ex1;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by stories2 on 2017. 9. 18..
 */

public interface RetrofitRouter {
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://i2max-ml.xyz/MobileCompute/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    @GET("ex1.php")
    Call<ApiResponse> OpenApiTest(
            @Query("code") String code,
            @Query("type") String type,
            @Query("phone") String phone,
            @Query("api") String api
    );
}
