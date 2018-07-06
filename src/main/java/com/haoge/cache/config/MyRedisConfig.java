package com.haoge.cache.config;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.haoge.cache.bean.Department;
import com.haoge.cache.bean.Employee;

@Configuration // 表明这是一个配置类
public class MyRedisConfig {
	// 自定义RedisTemplate,但是改变默认的序列化器
	//默认这个组件的ID为其名称，即empRedisTemplate
	@Bean // 将我们自定义的RedisTemplate加在容器中
	public RedisTemplate<Object, Employee> empRedisTemplate(RedisConnectionFactory redisConnectionFactory)
			throws UnknownHostException {
		RedisTemplate<Object, Employee> template = new RedisTemplate<Object, Employee>();
		template.setConnectionFactory(redisConnectionFactory);
		Jackson2JsonRedisSerializer<Employee> serializer = new Jackson2JsonRedisSerializer<Employee>(Employee.class);
		template.setDefaultSerializer(serializer);
		return template;
	}

	@Bean // 将我们自定义的RedisTemplate加在容器中
	public RedisTemplate<Object, Department> deptRedisTemplate(RedisConnectionFactory redisConnectionFactory)
			throws UnknownHostException {
		RedisTemplate<Object, Department> template = new RedisTemplate<Object, Department>();
		template.setConnectionFactory(redisConnectionFactory);
		Jackson2JsonRedisSerializer<Department> serializer = new Jackson2JsonRedisSerializer<Department>(
				Department.class);
		template.setDefaultSerializer(serializer);
		return template;
	}

	// 自定义empLoyeeCacheManager
	@Primary//当容器中有多个CacheManager的时候，使用Primary设置默认的缓存管理器
	@Bean
	public RedisCacheManager empLoyeeCacheManager(RedisTemplate<Object, Employee> empRedisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(empRedisTemplate);
		//缓存在redis中的数据的key值会自动拼接一个前缀，默认是拼接cacheName作为key的前缀
//		cacheManager.setUsePrefix(true);
		return cacheManager;
	}
	
	// 自定义deptCacheManager
	@Bean
	public RedisCacheManager deptCacheManager(RedisTemplate<Object, Department> deptRedisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(deptRedisTemplate);
		cacheManager.setUsePrefix(true);
		return cacheManager;
	}
}
