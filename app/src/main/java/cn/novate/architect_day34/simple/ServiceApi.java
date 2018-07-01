package cn.novate.architect_day34.simple;


import cn.novate.architect_day34.retrofit.Call;
import cn.novate.architect_day34.retrofit.http.GET;
import cn.novate.architect_day34.retrofit.http.Query;

/**
 * Email: 2185134304@qq.com
 * Created by Novate 2018/6/30 16:46
 * Version 1.0
 * Params:
 * Description:    请求后台访问数据的 接口类
*/

public interface ServiceApi {
    // 接口涉及到解耦，userLogin 方法是没有任何实现代码的
    // 如果有一天要换其他的网络框架 比如：GoogleHttp

    @GET("LoginServlet")// 登录接口 GET(相对路径)
    Call<UserLoginResult> userLogin(
            // @Query(后台需要解析的字段)
            @Query("userName") String userName,
            @Query("password") String userPwd);

    // POST

    // 上传文件怎么用？
}
