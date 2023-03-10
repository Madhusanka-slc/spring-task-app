package lk.ijse.dep9.app;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@ComponentScan
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class WebRootConfig {
    @Bean
    public JndiObjectFactoryBean dataSource(){
        JndiObjectFactoryBean jndi = new JndiObjectFactoryBean();
        jndi.setJndiName("java:comp/env/jdbc/task-app");
        jndi.setExpectedType(DataSource.class);
        return jndi;

    }
    @Bean
    public PlatformTransactionManager transactionManager(DataSource ds){
        return new DataSourceTransactionManager(ds);

    }



//    @Bean(destroyMethod = "close")
    @Bean
    @RequestScope

    public Connection connection(DataSource ds) throws  SQLException {

        return  DataSourceUtils.getConnection(ds);

    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource ds){
        return new JdbcTemplate(ds);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
