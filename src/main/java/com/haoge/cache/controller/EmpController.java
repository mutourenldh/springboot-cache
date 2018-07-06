package com.haoge.cache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haoge.cache.bean.Employee;
import com.haoge.cache.service.EmployeeService;
@CacheConfig(cacheNames="emp")
@RestController
@RequestMapping(value="/emp")
public class EmpController {
	@Autowired
	EmployeeService employeeService;
	//测试地址   http://localhost:8080/emp/emp/1
	/**
	 * Cacheable注解作用：将方法的返回结果进行缓存，以后如果是获取相同的数据，则从缓存中获取，不需要查询数据库
	 * cacheManager：管理cache组件的，真正对缓存的crud操作在缓存组件中进行，每一个缓存组件都有自己对应的名字
	 * Cacheable中的几个属性对应的意思：
	 * 		cacheNames/value:用来指定缓存组件的名字
	 * 		key:缓存数据时使用的key值可以用这个属性来指定，默认是使用方法参数的值，如这个方法中使用的是id为key值
	 * 			我们也可以编写SpEL表达式来执行key值。如 #id,   #a0   #p0   #root.args[0]
	 * 		keyGenerator:key值的生成器，我们可以自己指定key的生成器的组件ID。key和keyGenerator:key不能同时使用
	 * 		cacheManager:指定缓存管理器  ;   cacheResolver:指定缓存解析器
	 * 		condition:指定在符合情况下才能进行缓存
	 * 		unless:否定缓存，当unless的条件结果为true时，返回结果不进行缓存。也可以获取到结果再进行缓存。
	 * 			    如unless="#result==null"即返回结果为空的时候不进行缓存
	 * 		sync:是否适用异步模式
	 * 
	 */
	@Cacheable(cacheNames= {"emp"})
	@GetMapping(value="/emp/{id}")
	public Employee getEmpById(@PathVariable("id") Integer id) {
		
		Employee emp = employeeService.getEmpById(id);
		return emp;
	}
	
	@CachePut(value="emp",key="#employee.id")
	@RequestMapping(value="/upd")
	public Employee updateEmployee(Employee employee) {
		Employee updateEmployee = employeeService.updateEmployee(employee);
		return updateEmployee;
	}
	@RequestMapping(value="/add")
	public Employee insertEmployee(Employee employee) {
		employeeService.insertEmployee(employee);
		return employee;
	}
	
	@CacheEvict(value="emp",beforeInvocation=true,allEntries=true)
	@RequestMapping(value="/del")
	public Integer deleteEmployee(Integer id) {
		System.out.println(id);
		employeeService.deleteEmployee(id);
		return id;
	}
	
	@RequestMapping(value="/getEmpBylastName")
	public Employee getEmpBylastName(String lastName) {
		Employee emp = employeeService.getEmpBylastName(lastName);
		return emp;
	}
}
