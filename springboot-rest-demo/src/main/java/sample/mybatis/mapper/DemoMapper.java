package sample.mybatis.mapper;

import org.springframework.data.repository.query.Param;

import sample.mybatis.domain.DemoDTO;

public interface DemoMapper {

//	@Insert("insert into test_table (test_column) values (#{testColumn})")
//	public void insert(DemoDTO demoDTO) throws Exception;
//
//	@Select("select * from test_table where test_column = #{testColumn}")
//	@Results(value = { @Result(property = "testColumn", column = "test_column") })
//	public DemoDTO select(String testColumn) throws Exception;
//
//	@Update("update test_table set test_column = #{testColumn}")
//	public void update(DemoDTO demoDTO) throws Exception;
//
//	@Delete("delete from test_table where test_column = #{testColumn}")
//	public void delete(String testColumn) throws Exception;

	public void setData(@Param("testColumn") String testColumn) throws Exception;

	public DemoDTO getData(@Param("testColumn") String testColumn) throws Exception;

}