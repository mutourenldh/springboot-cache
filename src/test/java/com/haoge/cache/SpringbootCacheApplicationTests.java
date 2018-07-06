package com.haoge.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.haoge.cache.bean.Employee;
import com.haoge.cache.mapper.EmployeeMapper;

import net.minidev.json.JSONObject;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootCacheApplicationTests {
	
	// redis 的自动配置类RedisAutoConfiguration
	@Autowired
	StringRedisTemplate stringRedisTemplate;//主要是用来操作字符串的，key和value都是字符串
	@Autowired
	RedisTemplate<Object,Object> redisTemplate;//key和value可自己进行设置
	@Autowired
	EmployeeMapper employeeMapper;
	//自动注入我们自己自定义的RedisTemplate
	@Autowired
	RedisTemplate<Object, Employee> empRedisTemplate;
	
	@Autowired
	RedisTemplate<Object, Object> objRedisTemplate;
	@Qualifier("empLoyeeCacheManager")
	@Autowired
	RedisCacheManager empLoyeeCacheManager;
	/**
	 * redis中常见的五大数据类型：String,List(列表),Set(集合)，Hash(散列),Zset(有序集合)
	 * 
	 */
	@Test
	public void test01() {
//		stringRedisTemplate分别用来操作五种数据类型的方法
//		redisTemplate中也有对应的用来操作数据的五种方法
//		stringRedisTemplate.opsForValue();
//		stringRedisTemplate.opsForList();
//		stringRedisTemplate.opsForSet();
//		stringRedisTemplate.opsForHash();
//		stringRedisTemplate.opsForZSet();
//		stringRedisTemplate.opsForValue().set("aa", "aa");
		String string = stringRedisTemplate.opsForValue().get("aa");
		System.out.println(string);
	}
	@Test
	public void test02() {
		Employee employee = employeeMapper.getEmpById(1);
////		//使用自定义的序列化规则操作对象
//		redisTemplate.opsForValue().set("emp01", employee);
//		empRedisTemplate.opsForValue().set("emp01", employee);
//		 Employee object = empRedisTemplate.opsForValue().get("emp01");
//		 
//		System.out.println(object);
//		 Object object1 =  objRedisTemplate.opsForValue().get("emp01");
//		 Class<? extends Object> class1 = object1.getClass();
//		 System.out.println(class1);
//			System.out.println(object1);
//		Employee object = (Employee) redisTemplate.opsForValue().get("emp01");
//		System.out.println(object);
//		System.out.println(object.getClass());
		Cache cache = empLoyeeCacheManager.getCache("emp");
//		cache.put("emp02", employee);
		ValueWrapper valueWrapper = cache.get("emp02");
		System.out.println(valueWrapper.getClass());
		System.out.println(valueWrapper.toString());
	}

}
