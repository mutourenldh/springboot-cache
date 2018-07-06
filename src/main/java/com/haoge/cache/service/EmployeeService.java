package com.haoge.cache.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.haoge.cache.bean.Employee;
import com.haoge.cache.mapper.EmployeeMapper;

@Service
public class EmployeeService {
	@Autowired
	EmployeeMapper employeeMapper;

	public Employee getEmpById(Integer id) {
		System.out.println("查询"+id+"号员工");
		Employee emp = employeeMapper.getEmpById(id);
		return emp;
	}

	public Employee updateEmployee(Employee employee) {
		employeeMapper.updateEmployee(employee);
		return employee;
	}

	public void insertEmployee(Employee employee) {
		employeeMapper.insertEmployee(employee);
	}

	public void deleteEmployee(Integer id) {
		employeeMapper.deleteEmployee(id);
	}
	//@Caching注解的作用：定义复杂的缓存规则
	@Caching(
			cacheable= {
					@Cacheable(value="emp",key="#lastName")
			},
			put= {
				@CachePut(value="emp",key="#result.id"),	
				@CachePut(value="emp",key="#result.email")
			}
	)
	public Employee getEmpBylastName(String lastName) {
		Employee employee=employeeMapper.getEmpBylastName(lastName);
		return employee;
	}
}
