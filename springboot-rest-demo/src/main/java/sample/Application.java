package sample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


//@ImportResource("classpath*:config.xml")

@SpringBootApplication
@EnableScheduling
@MapperScan(value = { "sample.mybatis.mapper" })
public class Application {

	/**
	 * start
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		SpringApplication.run(new Object[] { Application.class }, args);
	}

}