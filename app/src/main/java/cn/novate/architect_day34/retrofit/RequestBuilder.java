package cn.novate.architect_day34.retrofit;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Email: 2185134304@qq.com
 * Created by Novate 2018/6/30 18:02
 * Version 1.0
 * Params:
 * Description:
*/

public class RequestBuilder {

    ParameterHandler<Object>[] parameterHandlers ;
    Object[] args ;
    HttpUrl.Builder httpUrl ;

    public RequestBuilder(String baseUrl, String relativeUrl, String httpMethod, ParameterHandler<?>[] parameterHandlers, Object[] args) {
        this.parameterHandlers = (ParameterHandler<Object>[]) parameterHandlers;
        this.args = args ;
        httpUrl = HttpUrl.parse(baseUrl + relativeUrl).newBuilder() ;
    }

    public Request build() {
        //
        int count = args.length ;
        for (int i = 0 ; i < count ; i++) {
            // userName = Novate
            parameterHandlers[i].apply(this , args[i]);
        }


        // 在这里构建一个请求
        Request request = new Request.Builder().url(httpUrl.build()).build();
        return request;
    }

    public void addQueryName(String key, String value) {
        // userName = Novate&userPwd = 123456
        httpUrl.addQueryParameter(key , value) ;
    }
}
