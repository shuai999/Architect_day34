package cn.novate.architect_day34.retrofit.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Email: 2185134304@qq.com
 * Created by Novate 2018/6/30 16:39
 * Version 1.0
 * Params:
 * Description:
*/

@Retention(RetentionPolicy.RUNTIME)    // 运行时注解
@Target(ElementType.METHOD)  // 注解放在方法上边
public @interface POST {
    String value();
}
