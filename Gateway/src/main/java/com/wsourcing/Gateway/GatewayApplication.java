package com.wsourcing.Gateway;

import com.wsourcing.Gateway.filters.LogFilter;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.filters.CorsFilter;
import org.apache.http.client.HttpClient;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;
import org.apache.catalina.connector.Connector;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import javax.net.ssl.SSLContext;
import java.util.Arrays;

//@Controller
//@EnableAutoConfiguration
//@Controller
//@EnableEurekaServer
//@Controller
//@EnableDiscoveryClient
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
//@EnableResourceServer
//@EnableFeignClients



public class GatewayApplication {


	public static void main(String[] args) {

		SpringApplication.run(GatewayApplication.class, args);
	}

}
