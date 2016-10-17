package sample.mybatis.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sample.mybatis.domain.DemoDTO;
import sample.mybatis.mapper.DemoMapper;

@RestController
@RequestMapping("/mybatis")
public class MybatisController {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	@Autowired
	private DemoMapper demoMapper;

	
	/**
	 * inset
	 * @param testStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/{testStr}")
	public Map<String, Object> getInsertTest(@PathVariable("testStr") String testStr) throws Exception {

//		DemoDTO demoDTO = new DemoDTO();
//		demoDTO.setTestColumn(testStr);
//		demoMapper.insert(demoDTO);
		
		demoMapper.setData(testStr);

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "mybatis insert successfully");
		response.put("testStr", testStr);
		return response;
	}
	
	/**
	 * select
	 * @param testStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{testStr}")
	public Map<String, Object> getSelectTest(@PathVariable("testStr") String testStr) throws Exception {

//		DemoDTO demoDTO = demoMapper.select(testStr);
//		String resultStr = demoDTO.getTestColumn();
		
		DemoDTO demoDTO = demoMapper.getData(testStr);

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "mybatis select successfully");
		response.put("testStr", demoDTO);
		return response;
	}

}