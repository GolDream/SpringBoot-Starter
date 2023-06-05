package com.goldream.middleware.whitelist;

import com.alibaba.fastjson.JSON;
import com.goldream.middleware.whitelist.annotation.DoWhiteList;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

//切面逻辑实现
@Aspect // 定义切面类
@Component //将类生成bean对象
public class DoJoinPoint {
    private Logger logger = LoggerFactory.getLogger(DoJoinPoint.class);
    //    @Resource
    @Value("${goldream.whitelist.users}")
    private String whiteListConfig;

    //    定义切点
    @Pointcut("@annotation(com.goldream.middleware.whitelist.annotation.DoWhiteList)")
    public void aopPoint() {

    }

    //    对方法增强的植入动作=>当调用已经加了自定义注解@DoWhiteList的方法时，会先进入到此切点增强的方法
//    这个时候就能进行方法的操作动作了比如我们实现白名单用户的拦截还是放行
    @Around("aopPoint()")
    public Object doRouter(ProceedingJoinPoint jp) throws Throwable {
//        获取内容
        Method method = getMethod(jp);
        DoWhiteList whiteList = method.getAnnotation(DoWhiteList.class);
//        获取字段值
        String keyValue = getFieldValue(whiteList.key(), jp.getArgs());
        logger.info("middleware whitelist handler method:{} val:{}", method.getName(), keyValue);
        if (null == keyValue || "".equals(keyValue)) return jp.proceed();
        String[] splits = whiteListConfig.split(",");
//        白名单过滤
        for (String str : splits) {
            if (keyValue.equals(str)) {
//                放行
                return jp.proceed();
            }
        }
//        拦截
        return returnObject(whiteList, method);
    }

    private Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        Signature sig = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) sig;
        return jp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }

    //    返回对象
    private Object returnObject(DoWhiteList whiteList, Method method) throws InstantiationException, IllegalAccessException {
        Class<?> returnType = method.getReturnType();
        String returnJson = whiteList.returnJson();
        if ("".equals(returnJson)) {
            return returnType.newInstance();
        }
        return JSON.parseObject(returnJson, returnType);
    }

    //    获取属性值
    private String getFieldValue(String field, Object[] args) {
        String filedValue = null;
        for (Object arg : args) {
            try {
                if (null == filedValue || "".equals(filedValue)) {
                    filedValue = BeanUtils.getProperty(arg, field);
                } else break;
            } catch (Exception e) {
                if (args.length == 1) {
                    return args[0].toString();
                }
            }

        }
        return filedValue;
    }
}
