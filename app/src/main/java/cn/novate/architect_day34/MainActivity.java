package cn.novate.architect_day34;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import cn.novate.architect_day34.retrofit.Call;
import cn.novate.architect_day34.retrofit.Callback;
import cn.novate.architect_day34.retrofit.Response;
import cn.novate.architect_day34.retrofit.Retrofit;
import cn.novate.architect_day34.simple.RetrofitClient;
import cn.novate.architect_day34.simple.UserLoginResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RetrofitClient.getServiceApi().userLogin("Novate" , "123456")
                .enqueue(new Callback<UserLoginResult>() {
                    @Override
                    public void onResponse(Call<UserLoginResult> call, Response<UserLoginResult> response) {
                        Log.e("TAG" , response.body.toString()) ;

                    }

                    @Override
                    public void onFailure(Call<UserLoginResult> call, Throwable t) {

                    }
                });
    }
}
