package cn.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.jk.domain.Dept;
import cn.itcast.jk.domain.User;
import cn.itcast.jk.service.DeptService;
import cn.itcast.jk.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class Test {

	@Autowired
	private DeptService d;
	@Autowired
	private UserService s;

	@org.junit.Test
	public void test() {
		Dept dept = new Dept();
		dept.setState(1);
		dept.setDeptName("开发部");
		dept.setParentDept(null);
		d.saveOrUpdate(dept);
	}
	@org.junit.Test
	public void test2() {
		List<User> find = s.find("from User", User.class, null);
		System.out.println(find);
	}
}
