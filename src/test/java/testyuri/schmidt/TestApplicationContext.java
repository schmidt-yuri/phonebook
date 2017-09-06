package testyuri.schmidt;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.Transactional;

import yuri.schmidt.dao.RecordDao;
import yuri.schmidt.dao.RecordDaoImpl;
import yuri.schmidt.model.PersonPhone;

@Configuration
@ComponentScan({"testyuri.schmidt", "yuri.schmidt.dao"})
public class TestApplicationContext {

	@Bean
	public DataSource dataSource(){
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder
									.setType(EmbeddedDatabaseType.HSQL)
									.setName("phone_book")
									.addScript("dbschema.sql")
									.addScript("test-data.sql")
									.setScriptEncoding("UTF-8")
									.build();
		return db;
	}
//	@Bean
//	
//	public DriverManagerDataSource dataSource(){
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
//		dataSource.setUrl("jdbc:hsqldb:mem:phone_book");
//		dataSource.setUsername("sa");
//		dataSource.setPassword("");
//		return dataSource;
//	}

	
	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource){
		LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
		sfb.setDataSource(dataSource);
		sfb.setPackagesToScan(new String[]{"yuri.schmidt.model"});
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "update");
		sfb.setHibernateProperties(props);
		
		return sfb;
	}
	@Bean
	public RecordDao recordDao() {
		return new RecordDaoImpl();
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate(){
		return new JdbcTemplate(dataSource());
	}
	
	

}
