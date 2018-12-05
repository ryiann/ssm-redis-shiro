# ssm-redis-shiro

Reids安装请参考 [CentOS 安装 Redis][1]

项目地址：

**整合之前:**   
[ssm][2]   
**整合之后:**   
[ssm-redis-shiro][3]

本文是以ssm框架项目为基础，整合redis，目的为了实现每次 select 查询时，首先判断redis中是否有缓存，有就读取，没有就查询数据库并保存到redis中，下次再查询的话就能从缓存中读取到了

---

## 引入redis依赖

```xml
<!--  jedis  -->
<!--  https://mvnrepository.com/artifact/redis.clients/jedis  -->
<dependency>
	<groupId>redis.clients</groupId>
		<artifactId>jedis</artifactId>
	<version>2.7.3</version>
</dependency>
<!--  spring-data-redis  -->
<!--  https://mvnrepository.com/artifact/org.springframework.data/spring-data-redis  -->
<dependency>
	<groupId>org.springframework.data</groupId>
		<artifactId>spring-data-redis</artifactId>
	<version>1.6.1.RELEASE</version>
</dependency>
```
Jedis
:	java操作redis通常使用的是jedis，通过代码来操作redis的数据存储读取等

Spring-Data-Redis
:	spring也为redis提供了支持，就是在spring-data模块中的spring-data-redis（SDR），它一部分是基于Jedis客户端的API封装，另一部分是对Spring容器的整合

## 添加Redis相关配置

在参数配置中添加redis连接参数

- applicationContext.properties

```xml
#redis
redis.host=redis.lqiao.top
redis.port=6379
redis.pass=Hello123
redis.maxIdle=300
redis.maxActive=600
redis.maxWait=1000
redis.testOnBorrow=true
redis.timeout=100000
redis.cacheExpireTime=3600
```
增加redis配置

- applicationContext-redis.xml

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd">

  <!-- jedis 配置 -->
  <bean id="poolConfig" class="redis.clients.jedis.JedisPoolConfig"> 
    <property name="maxIdle" value="${redis.maxIdle}" />
    <property name="maxTotal" value="${redis.maxActive}" />
    <property name="maxWaitMillis" value="${redis.maxWait}"></property>
    <property name="testOnBorrow" value="${redis.testOnBorrow}" /> 
  </bean>

  <!-- redis服务器中心 -->
  <bean id="connectionFactory" class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory" 
    p:host-name="${redis.host}"
    p:port="${redis.port}"
    p:password="${redis.pass}" 
    p:pool-config-ref="poolConfig"/>

  <bean id="redisTemplate" class="org.springframework.data.redis.core.RedisTemplate">
    <property name="connectionFactory" ref="connectionFactory"/>
    <property name="keySerializer">
      <bean class="org.springframework.data.redis.serializer.StringRedisSerializer"/>
    </property>
    <property name="valueSerializer">
      <bean class="org.springframework.data.redis.serializer.JdkSerializationRedisSerializer"/>
    </property>
  </bean>

  <!-- cache 缓存拦截器配置 -->
  <bean id="methodCacheInterceptor" class="com.ryan.interceptor.MethodCacheInterceptor">
    <property name="redisUtil" ref="redisUtil"/>
    <property name="defaultCacheExpireTime" value="${redis.cacheExpireTime}"/>
    <!-- 禁用缓存的类名列表 -->
    <property name="targetNamesList">
      <list>
        <value></value>
      </list>
    </property>
    <!-- 禁用缓存的方法名列表 -->
    <property name="methodNamesList">
      <list>
        <value></value>
      </list>
    </property>
  </bean>

  <bean id="redisUtil" class="com.ryan.utils.RedisUtil">
    <property name="redisTemplate" ref="redisTemplate"/>
  </bean>

  <!--配置切面拦截方法 -->
  <aop:config proxy-target-class="true">
    <aop:pointcut id="controllerMethodPointcut" expression="execution(* com.ryan.service.impl.*.select*(..))"/>
    <aop:advisor advice-ref="methodCacheInterceptor" pointcut-ref="controllerMethodPointcut"/>
  </aop:config>

</beans>
```
**Spring AOP使用缓存：**

使用表达式`execution(* com.ryan.service.impl.*.select*(..))`来拦截service中所有以select开头的方法。这样所有涉及以select命名开头的查询方法每次进入方法之前都会进入我们自定义的MethodCacheInterceptor拦截器去判断是否有缓存



- MethodCacheInterceptor.java

```java
import com.ryan.utils.RedisUtil;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Redis缓存过滤器
 * @author YoriChen
 * @date 2018/6/21
 */
public class MethodCacheInterceptor implements MethodInterceptor {
    private static final Logger logger = LogManager.getLogger(MethodCacheInterceptor.class);

    private RedisUtil redisUtil;

    /**
     * 不加入缓存的service名称
     */
    private List<String> targetNamesList;

    /**
     * 不加入缓存的方法名称
     */
    private List<String> methodNamesList;

    /**
     * 缓存默认的过期时间
     */
    private String defaultCacheExpireTime;

    /**
     * 初始化读取不需要加入缓存的类名和方法名称
     */
    public MethodCacheInterceptor() {
        try {
            // 分割字符串 这里没有加入任何方法
            String[] targetNames = {};
            String[] methodNames = {};

            // 创建list
            targetNamesList = new ArrayList<String>(targetNames.length);
            methodNamesList = new ArrayList<String>(methodNames.length);
            Integer maxLen = targetNames.length > methodNames.length ? targetNames.length : methodNames.length;
            // 将不需要缓存的类名和方法名添加到list中
            for (int i = 0; i < maxLen; i++) {
                if (i < targetNames.length) {
                    targetNamesList.add(targetNames[i]);
                }
                if (i < methodNames.length) {
                    methodNamesList.add(methodNames[i]);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object value = null;

        String targetName = invocation.getThis().getClass().getName();
        String methodName = invocation.getMethod().getName();
        // 不需要缓存的内容
        if (!isAddCache(targetName, methodName)) {
            // 执行方法返回结果
            return invocation.proceed();
        }
        Object[] arguments = invocation.getArguments();
        String key = getCacheKey(targetName, methodName, arguments);
        try {
            // 判断是否有缓存
            if (redisUtil.exists(key)) {
                return redisUtil.get(key);
            }
            // 写入缓存
            value = invocation.proceed();
            if (value != null) {
                final String tKey = key;
                final Object tValue = value;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        redisUtil.set(tKey, tValue, Long.parseLong(defaultCacheExpireTime));
                    }
                }).start();
            }
        } catch (Exception e) {
            logger.error(e);
            if (value == null) {
                return invocation.proceed();
            }
        }
        return value;
    }

    /**
     * 是否加入缓存
     *
     * @return
     */
    private boolean isAddCache(String targetName, String methodName) {
        boolean flag = true;
        if (targetNamesList.contains(targetName)
                || methodNamesList.contains(methodName) || targetName.contains("$$EnhancerBySpringCGLIB$$")) {
            flag = false;
        }
        return flag;
    }

    /**
     * 创建缓存key
     *
     * @param targetName
     * @param methodName
     * @param arguments
     */
    private String getCacheKey(String targetName, String methodName, Object[] arguments) {
        StringBuffer sbu = new StringBuffer();
        sbu.append(targetName).append("_").append(methodName);
        if ((arguments != null) && (arguments.length != 0)) {
            for (int i = 0; i < arguments.length; i++) {
                sbu.append("_").append(arguments[i]);
            }
        }
        return sbu.toString();
    }

    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public void setTargetNamesList(List<String> targetNamesList) {
        this.targetNamesList = targetNamesList;
    }

    public void setMethodNamesList(List<String> methodNamesList) {
        this.methodNamesList = methodNamesList;
    }

    public void setDefaultCacheExpireTime(String defaultCacheExpireTime) {
        this.defaultCacheExpireTime = defaultCacheExpireTime;
    }
```

- RedisUtil.java

```java
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 * @author YoriChen
 * @date 2018/6/21
 */
public class RedisUtil {

    private static final Logger logger = LogManager.getLogger(RedisUtil.class);

    private RedisTemplate<Serializable, Object> redisTemplate;

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            logger.info("batches deletes value by key : {}", key);
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0){
            logger.info("batches deletes key : {}", keys);
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            logger.info("delete value by key : {}", key);
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        logger.info("exists value : {}", redisTemplate.hasKey(key));
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        logger.info("get value by key : {}" , result);
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            logger.info("set key : {}, value : {}", key , value);
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            logger.info("set key : {}, value : {}, expireTime : {}", key , value, expireTime);
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public void setRedisTemplate(RedisTemplate<Serializable, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
```

由于我们使用的是Spring AOP切面去判断使用Redis缓存，这就很大程度上不需要我们再去修改项目代码，只需要引入两个redis依赖（jedis、spring-data-redis）,两个java类（MethodCacheInterceptor.java、RedisUtil.java），一个redis配置文件就可以使用了，最后直接访问就可以了

 



---------

[1]: https://blog.csdn.net/yori_chen/article/details/79780692
[2]: https://github.com/ryiann/ssm-parent
[3]: https://github.com/ryiann/ssm-redis-shiro
