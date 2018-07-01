package cn.novate.architect_day34.retrofit;

/**
 * Email: 2185134304@qq.com
 * Created by Novate 2018/6/30 18:38
 * Version 1.0
 * Params:
 * Description:
*/

public interface ParameterHandler<T> {
    public void apply(RequestBuilder requestBuilder,T value);

    // 这里有很多策略：比如Query、QueryMap、Part、Filed等等
    class Query<T> implements ParameterHandler<T>{
        // 代表的是：保存的参数的key，就是 给后台要提交的 userName、userPwd
        private String key;
        public Query(String key){
            this.key = key;
        }

        @Override
        public void apply(RequestBuilder requestBuilder,T value) {
            // 添加到 Request中  value -> String 要经过一个工厂
            requestBuilder.addQueryName(key,value.toString());
        }
    }
    // 还有一些其他

}

