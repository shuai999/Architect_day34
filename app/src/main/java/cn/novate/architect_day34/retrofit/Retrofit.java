package cn.novate.architect_day34.retrofit;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.novate.architect_day34.simple.ServiceApi;
import okhttp3.OkHttpClient;

/**
 * Email: 2185134304@qq.com
 * Created by Novate 2018/6/30 18:43
 * Version 1.0
 * Params:
 * Description:
*/

public class Retrofit {

    final String baseUrl;
    final okhttp3.Call.Factory callFactory;

    // ConcurrentHashMap: 线程安全的 HashMap
    private Map<Method,ServiceMethod> serviceMethodMapCache = new ConcurrentHashMap<>();


    public Retrofit(Builder builder){
        this.baseUrl = builder.baseUrl ;
        this.callFactory = builder.callFactory ;
    }

    public <T>T create(Class<T> service) {
        // 重点在这里
        // 第1步：动态代理
        return (T)Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 每个方法的执行 都会来到这个动态代理
                Log.e("TAG" , "methodName: " + method.getName()) ;


                // 判断是不是 Object 的方法
                if (method.getDeclaringClass() == Object.class){
                    return method.invoke(this , args) ;
                }

                // 第2步：解析参数注解
                ServiceMethod serviceMethod = loadServiceMethod(method) ;

                // 第3步：封装 OkHttpCall
                OkHttpCall okHttpCall = new OkHttpCall(serviceMethod , args) ;
                return okHttpCall;
            }
        });
    }



    private ServiceMethod loadServiceMethod(Method method) {
        // 享元设计模式，其实就是做一个缓存
        ServiceMethod serviceMethod = serviceMethodMapCache.get(method);
        if (serviceMethod == null){
            serviceMethod = new ServiceMethod.Builder(this , method).build() ;
            serviceMethodMapCache.put(method , serviceMethod) ;
        }

        return serviceMethod;
    }


    public static class Builder {
        String baseUrl ;
        okhttp3.Call.Factory callFactory ;
        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl ;
            return this ;
        }

        public Builder client(okhttp3.Call.Factory callFactory) {
            this.callFactory = callFactory ;
            return this ;
        }


        public Retrofit build() {
            if (callFactory == null){
                callFactory = new OkHttpClient() ;
            }
            return new Retrofit(this);
        }
    }
}
