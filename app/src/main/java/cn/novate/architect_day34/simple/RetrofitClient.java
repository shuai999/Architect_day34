package cn.novate.architect_day34.simple;

import android.util.Log;


import cn.novate.architect_day34.retrofit.Retrofit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Email: 2185134304@qq.com
 * Created by Novate 2018/6/30 17:36
 * Version 1.0
 * Params:
 * Description:
*/

public class RetrofitClient {
    private final static ServiceApi mServiceApi;

    static {
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder().addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override    // 处理不打印log日志问题
                    public void log(String message) {
                        Log.e("TAG",message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                // 访问后台接口的主路径
                .baseUrl("http://192.168.8.169:8080/OkHttpServer/")
                // 添加 OkHttpClient,不添加默认就是 光杆 OkHttpClient
                .client(okHttpClient)
                .build();

        // 创建一个 实例对象
        mServiceApi = retrofit.create(ServiceApi.class);
    }

    public static ServiceApi getServiceApi() {
        return mServiceApi;
    }

}
