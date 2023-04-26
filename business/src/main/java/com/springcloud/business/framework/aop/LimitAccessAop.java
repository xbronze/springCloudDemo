package com.springcloud.business.framework.aop;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.springcloud.business.framework.annotation.LimitAccess;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author: xbronze
 * @date: 2023-04-26 16:34
 * 使用切面实现url拦截:
 * 切片的实现分为2个步   A: 切入点(使用 @Before  @After  @Around 等注解实现 : 用于表示 在那些方法上(注解参数)起作用 ,在什么时候(注解类型)起作用)
 *                   B: 增强  (起作用的时候执行的业务逻辑)
 *    具体如下:
 *       1. 定义一个类,添加 @Aspect 注解,表明这是一个切片类,同时加上@Component注解将这个类在spring容器中声明
 *       2. 在你的增强逻辑的方法上使用 @Before  @After  @Around 等注解,可以传递参数 ProceedingJoinPoint 对象,
 *          ProceedingJoinPoint对象类似于拦截器的 handler,可获取增强的方法的信息,类的名称和方法名称以及方法参数等等
 *       3. 编写逻辑代码完善要增强方法的逻辑
 */
@Slf4j
@Aspect
@Component
public class LimitAccessAop {

    /**
     * 不同的接口，不同的限流控制
     * map的key为Limiter.key
     */
    private final Map<String, RateLimiter> limiterMap = Maps.newConcurrentMap();

    /**
     * @Before  在要增强方法之前执行
     * @After   在要增强方法之后执行
     * 在要增强方法上环绕执行(前后都可以执行)
     * 注解参数: execution(* com.qxl.web.controller.userController.*(..))
     *            表示此方法在 com.qxl.web.controller.userController下的任何方法,方法包含任何参数,任何返回值 的方法上都能执行
     *         第一个  * : 表示方法返回值为任意值都能执行此增强逻辑
     *         第二个  * : userController 下的任何方法都能执行此增强逻辑
     *         (..)   : 表示方法里的参数为任意值 (无论参数多少,大小,类型)都可以执行此增强逻辑
     * 注解参数: @annotation(com.springcloud.business.framework.annotation.LimitAccess)
     *            表示任何引入com.springcloud.business.framework.annotation.LimitAccess自定义注解的方法都可以执行此增强逻辑
     * @throws Throwable
     */
    @Around(value = "@annotation(com.springcloud.business.framework.annotation.LimitAccess)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 拿到LimitAccess注解
        LimitAccess limitAccess = method.getAnnotation(LimitAccess.class);
        if (limitAccess != null) {
            String key = limitAccess.key();
            RateLimiter rateLimiter = null;
            // 验证缓存是否有命中key
            if (!limiterMap.containsKey(key)) {
                // 创建令牌桶，每秒最大查询数
                rateLimiter = RateLimiter.create(limitAccess.permitsPerSecond());
                limiterMap.put(key, rateLimiter);
                log.info("新建了令牌桶={}，容量={}", key, limitAccess.permitsPerSecond());
            }
            rateLimiter = limiterMap.get(key);
            // 拿令牌，判断能否在指定时间内获取到令牌, 如果不能获取立即返回 false
            boolean acquire = rateLimiter.tryAcquire(limitAccess.timeout(), limitAccess.timeunit());
            // 拿不到令牌，直接返回异常提示
            if (!acquire) {
                log.error("令牌桶={}， 获取令牌失败", key);
                return null;
            }
        }

        // proceed()方法用来调用ProceedingJoinPoint对象获取到的那个的方法(即执行切片要增强的那个方法),
        // proceed()方法返回的Object就是增强方法的返回值，
        // 如果proceed()方法传递了参数,会替换原来方法的参数
        return joinPoint.proceed();
    }

}
