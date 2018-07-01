package cn.novate.architect_day34.retrofit;

import cn.novate.architect_day34.simple.UserLoginResult;

/**
 * Email: 2185134304@qq.com
 * Created by Novate 2018/6/30 16:45
 * Version 1.0
 * Params:
 * Description:
*/

public interface Call<T> {
    void enqueue(Callback<T> callback);
}
