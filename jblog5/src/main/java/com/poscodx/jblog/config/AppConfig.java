package com.poscodx.jblog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.poscodx.jblog.config.app.DBConfig;
import com.poscodx.jblog.config.app.MyBatisConfig;
import com.poscodx.jblog.config.app.SecurityConfig;


@Configuration 
@EnableAspectJAutoProxy
@EnableTransactionManagement 
@ComponentScan({"com.poscodx.jblog.service", "com.poscodx.jblog.repository","com.poscodx.jblog.security"})
@Import({DBConfig.class,MyBatisConfig.class,SecurityConfig.class})
public class AppConfig {

	// EnableAspectJAutoProxy : <aop:aspectj-autoproxy/> 
	//  @EnableTransactionManagement   : <tx:annotation-driven />
	// <context:component-scan base-package="com.poscodx.jblog.service, com.poscodx.jblog.repository" />
}



