package cn.test;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.jk.domain.Dept;
import cn.itcast.jk.service.DeptService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class Test {

	@Autowired
	private DeptService d;
	@org.junit.Test
	public void test() {
		Dept dept = new Dept();
		dept.setState(1);
		dept.setDeptName("开发部");
		dept.setId("12");
		d.saveOrUpdate(dept);
	}
}
