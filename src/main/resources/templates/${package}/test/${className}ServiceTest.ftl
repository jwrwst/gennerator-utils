package ${package};


import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.config.annotation.Service;
import ${package}.service.${className}Service;
/**
 * Description: [XXXX的单元测试]
 * Created on ${date}
 * @author  ${uname}
 */
@Service
public class ${className}ServiceTest{

	private ${className}Service ${classNameLower}Service;
    ApplicationContext ctx;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		${classNameLower}Service = (${className}Service) ctx.getBean("${classNameLower}Service");
	}
	
	
	@Test
	public void queryPageTest(){
		
	}
	
	@Test
	public void query${className}DTOByConditionTest(){
		
	}
	
	@Test
	public void saveTest(){
		
	}
	
	@Test
	public void editTest(){
		
	}
	
	@Test
	public void deleteTest(){
		
	}
}