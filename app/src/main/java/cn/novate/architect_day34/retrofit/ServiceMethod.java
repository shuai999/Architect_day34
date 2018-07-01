package cn.novate.architect_day34.retrofit;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cn.novate.architect_day34.retrofit.http.GET;
import cn.novate.architect_day34.retrofit.http.POST;
import cn.novate.architect_day34.retrofit.http.Query;
import okhttp3.Request;
import okhttp3.ResponseBody;

/**
 * Email: 2185134304@qq.com
 * Created by Novate 2018/6/30 18:54
 * Version 1.0
 * Params:
 * Description:
*/

public class ServiceMethod {

    final Retrofit retrofit;
    final Method method;
    String httpMethod;
    String relativeUrl;
    final ParameterHandler<?>[] parameterHandlers;

    public ServiceMethod(Builder builder){
        this.retrofit = builder.retrofit;
        this.method = builder.method;
        this.httpMethod = builder.httpMethod;
        this.relativeUrl = builder.relativeUrl;
        this.parameterHandlers = builder.parameterHandlers;
    }

    public okhttp3.Call createNewCall(Object[] args) {

        // 还需要一个对象，专门用于添加参数的
        RequestBuilder requestBuilder = new RequestBuilder(retrofit.baseUrl , relativeUrl , httpMethod , parameterHandlers , args) ;

        // 添加参数
        String url = retrofit.baseUrl + relativeUrl ;
        return retrofit.callFactory.newCall(requestBuilder.build());
    }

    public <T>T parseBody(ResponseBody responseBody) {
        // 要获取解析的类型 T 获取方法返回值的类型

        // 获取返回值所有对象的泛型
        Type returnType = method.getGenericReturnType();
        Class <T> dataClass = (Class <T>) ((ParameterizedType) returnType).getActualTypeArguments()[0];
        // 解析工厂去转换
        Gson gson = new Gson();
        T body = gson.fromJson(responseBody.charStream(),dataClass);
        return body;
    }

    public static class Builder {
        final Retrofit retrofit ;
        final Method method ;
        final Annotation[] methodAnnotations ;
        String httpMethod;
        String relativeUrl;
        Annotation[][] parameterAnnotations ;
        final ParameterHandler<?>[] parameterHandlers ;

        public Builder(Retrofit retrofit, Method method) {
            this.retrofit = retrofit ;
            this.method = method ;
            methodAnnotations = method.getAnnotations() ;
            // 二维数组
            parameterAnnotations = method.getParameterAnnotations() ;
            parameterHandlers = new ParameterHandler[parameterAnnotations.length] ;
        }

        public ServiceMethod build() {
            // 在这里解析，OkHttp 请求的时候：url = baseUrl + relativeUrl(相对路径)、method
            for (Annotation methodAnnotation : methodAnnotations) {
                // 解析 GET、POST
                parseAnnotationMethod(methodAnnotation) ;
            }

            // 解析参数注解
            int count = parameterHandlers.length ;
            for (int i = 0 ; i < count ; i++) {
                Annotation parameter = parameterAnnotations[i][0];

                // 获取的是 Query、QueryMap等等参数注解 或者其他
                Log.e("TAG" , "parameter = " + parameter.annotationType().getName()) ;
                // 这里会涉及到 模板、策略设计模式
                if (parameter instanceof Query){
                    // 一个一个封装成 ParameterHandler，不同的参数注解 选择 不同的 策略
                    parameterHandlers[i]  = new ParameterHandler.Query<>(((Query) parameter).value()) ;
                }
            }

            return new ServiceMethod(this);
        }


        private void parseAnnotationMethod(Annotation methodAnnotation) {
            // 获取value，请求方法
            if (methodAnnotation instanceof GET){
                parseMethodAndPath("GET" , ((GET) methodAnnotation).value()) ;
            }else if (methodAnnotation instanceof POST){
                parseMethodAndPath("POST" , ((POST) methodAnnotation).value()) ;
            }
        }



        private void parseMethodAndPath(String method, String value) {
            this.httpMethod = method ;
            this.relativeUrl = value ;

        }
    }
}
