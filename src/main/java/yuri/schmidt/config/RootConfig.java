package yuri.schmidt.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import yuri.schmidt.dao.RecordDao;
import yuri.schmidt.dao.RecordDaoImpl;

@Configuration
@EnableWebMvc
@ComponentScan("yuri.schmidt")
public class RootConfig {
	@Bean
	public DriverManagerDataSource dataSources(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/phone_book");
		dataSource.setUsername("appuser");
		dataSource.setPassword("appuser");
		return dataSource;
	}

//	@Bean
//	public DriverManagerDataSource dataSource(){
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("org.hsqldb.JDBCDriver");
//		dataSource.setUrl("jdbc:hsqldb:hsql://localhost:");
//		dataSource.setUsername("SA");
//		dataSource.setPassword("");
//		return dataSource;
//	}
		
//	@Bean
//	public DataSource dataSource(){
//		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//		EmbeddedDatabase db = builder
//									.setType(EmbeddedDatabaseType.HSQL)
//									.addScript("dbschema.sql")
//									.addScript("test-data.sql")
//									.setScriptEncoding("UTF-8")
//									.build();
//		return db;
//	}

	
	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource){
		LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
		sfb.setDataSource(dataSource);
		sfb.setPackagesToScan(new String[]{"yuri.schmidt.model"});
		//sfb.setAnnotatedClasses(Student.class);
		Properties props = new Properties();
		props.setProperty("hibernate.default_schema", "phone_book");
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "update");
		sfb.setHibernateProperties(props);
		
		//sfb.setConfigLocation(new ClassPathResource("classpath:hibernate.cfg.xml"));
		return sfb;
	}
	@Bean
	public RecordDao recordDao() {
		return new RecordDaoImpl();
	}



}
