package sample.mybatis;


import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
public class MybatisConfiguration {

	/**
	 * set SqlSessionFactory for mybatis
	 * 
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		
		//xml 방법
		Resource[] arrResource = new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*Mapper.xml");
		sqlSessionFactoryBean.setMapperLocations(arrResource);
		
		return sqlSessionFactoryBean.getObject();
	}

}