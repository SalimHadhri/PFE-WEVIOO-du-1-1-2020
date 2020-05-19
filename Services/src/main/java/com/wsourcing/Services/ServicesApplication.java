package com.wsourcing.Services;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.client.MongoClients;
//@EnableAutoConfiguration
@SpringBootApplication
//@EnableDiscoveryClient
@EnableDiscoveryClient
public class ServicesApplication  {
//	private static final Log log = LogFactory.getLog(ServicesApplication .class);

	public static void main(String[] args) throws Exception {


	//	MongoOperations mongoOps = new MongoTemplate(MongoClients.create(), "wsourcingServices");

		SpringApplication.run(ServicesApplication.class, args);
	}

}
