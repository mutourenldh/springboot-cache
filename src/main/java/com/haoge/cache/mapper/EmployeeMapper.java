package com.haoge.cache.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.haoge.cache.bean.Employee;
@Mapper
public interface EmployeeMapper {
	//根据Id查询员工的接口
	@Select("select * from employee where id=#{id}")
	public Employee getEmpById(Integer id);
	@Update("update employee set lastName=#{lastName},email=#{email},gender=#{gender},d_id=#{dId} where id=#{id}")
	public void updateEmployee(Employee employee);
	
	@Update("insert into employee set lastName=#{lastName},email=#{email},gender=#{gender},d_id=#{dId}")
	public void insertEmployee(Employee employee);
	
	@Delete("delete from employee where id=#{id}")
	public void deleteEmployee(Employee employee);
}
