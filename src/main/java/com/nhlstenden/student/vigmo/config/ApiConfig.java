package com.nhlstenden.student.vigmo.config;

import org.modelmapper.*;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Properties;

@EnableWebMvc
@Configuration
@EnableTransactionManagement()
@ComponentScan("com.nhlstenden.student.vigmo")
@PropertySource("classpath:application.properties")
@EnableJpaRepositories("com.nhlstenden.student.vigmo.repositories")
@Import(SwaggerConfig.class)
public class ApiConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

    @Bean
    public DataSource datasource(Environment env) {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(Objects.requireNonNull(env.getProperty("database.driver")));
        ds.setUrl(env.getProperty("database.url"));
        ds.setUsername(env.getProperty("database.user"));
        ds.setPassword(env.getProperty("database.pass"));
        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        vendorAdapter.setGenerateDdl(true);

        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPackagesToScan("com.nhlstenden.student.vigmo.models");
        em.setDataSource(dataSource);
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        return em;
    }

    @Bean
    public PersistenceAnnotationBeanPostProcessor pABPP() {
        return new PersistenceAnnotationBeanPostProcessor();
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "none");
        properties.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
        properties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        return properties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    /**
     * Bean for {@link ModelMapper} to transform models into dtos.
     * Creates a custom converter for {@link LocalTime time} to transform time to a string
     * @return A ModelMapper instance wrapped by Spring
     */
    @Bean
    public ModelMapper mapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true);

        return modelMapperDateParser(modelMapperTimeParser(modelMapper));
    }


    private ModelMapper modelMapperDateParser(ModelMapper modelMapper) {
        Provider<LocalDate> localDateProvider = new AbstractProvider<LocalDate>() {
            @Override
            public LocalDate get() {
                return LocalDate.now();
            }
        };

        Converter<String, LocalDate> toStringDate = new AbstractConverter<String, LocalDate>() {
            @Override
            protected LocalDate convert(String source) {
                if(source == null || source.isEmpty()) return null;
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(source, format);
            }
        };

        modelMapper.createTypeMap(String.class, LocalDate.class);
        modelMapper.addConverter(toStringDate);
        modelMapper.getTypeMap(String.class, LocalDate.class).setProvider(localDateProvider);

        return modelMapper;
    }


    private ModelMapper modelMapperTimeParser(ModelMapper modelMapper) {
        Provider<LocalTime> localTimeProvider = new AbstractProvider<LocalTime>() {
            @Override
            public LocalTime get() {
                return LocalTime.now();
            }
        };

        Converter<String, LocalTime> toStringTime = new AbstractConverter<String, LocalTime>() {
            @Override
            protected LocalTime convert(String source) {
                if(source == null || source.isEmpty()) return null;
                DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                return LocalTime.parse(source, format);
            }
        };

        modelMapper.createTypeMap(String.class, LocalTime.class);
        modelMapper.addConverter(toStringTime);
        modelMapper.getTypeMap(String.class, LocalTime.class).setProvider(localTimeProvider);

        return modelMapper;
    }
}