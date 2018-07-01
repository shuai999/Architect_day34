package cn.novate.architect_day34.retrofit;

import android.util.Log;

import java.io.IOException;

import okhttp3.*;

/**
 * Email: 2185134304@qq.com
 * Created by Novate 2018/6/30 17:42
 * Version 1.0
 * Params:
 * Description:
*/

public class OkHttpCall<T> implements Call<T> {

    final ServiceMethod serviceMethod ;
    final Object[] args ;
    public OkHttpCall(ServiceMethod serviceMethod , Object[] args){
        this.serviceMethod = serviceMethod ;
        this.args = args ;
    }


    @Override
    public void enqueue(final Callback<T> callback) {
        // 在这里发起一个请求，给一个回调就可以了
        Log.e("TAG" , "正式的发起一个请求") ;

        okhttp3.Call call = serviceMethod.createNewCall(args) ;
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                if (callback != null){
                    callback.onFailure(OkHttpCall.this , e);
                }
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                // 解析Response -> Resposne<T> 回调
                Log.e("TAG" , response.body().toString()) ;
                // 涉及到解析，不能在这里写死，需要使用 ConvertFactory工厂
                Response rResponse = new Response() ;
                rResponse.body = serviceMethod.parseBody(response.body()) ;

                if (callback != null){
                    callback.onResponse(OkHttpCall.this , rResponse);
                }

            }
        });


    }

}
