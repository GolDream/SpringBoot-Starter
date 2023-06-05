package com.goldream.middleware.whitelist.annotation;

import java.lang.annotation.*;

//自定义注解 在需要使用到的白名单服务的接口上，添加次注解并配置必要的信息 接口入参提取字段属性名称、拦截后的返回信息

//@Retention注解的注解，元注解
//RetentionPolicy.RUNTIME 加了这个注解，它的信息会被待到JVM虚拟机运行时，当你调用方法时可以通过反射拿到注解信息
@Retention(RetentionPolicy.RUNTIME)
//@Target元注解 标记作用 自定义注解DoWhiteList要放在方法上
@Target(ElementType.METHOD)
@Inherited
public @interface DoWhiteList {
//    key:配置当前接口入参需要提取的属性
    String key() default "";
//    returnJson:在我们拦截到用户请求后需要给一个返回信息
    String returnJson() default "";
}
