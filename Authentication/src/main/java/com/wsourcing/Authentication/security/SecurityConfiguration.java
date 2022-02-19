package com.wsourcing.Authentication.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/*create custom security configuration class:
    @Configuration
    @EnableWebSecurity
    extends WebSecurityConfigurerAdapter
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    // URL configurations
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .logout().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/users/auth").permitAll()
                .antMatchers(HttpMethod.POST,"/users/**").permitAll()
                .antMatchers(HttpMethod.GET,"/users/**").permitAll()
                .antMatchers(HttpMethod.DELETE,"/users/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/users/**").permitAll()
                .antMatchers("/users/listUsers").hasRole("USER")
                .antMatchers("/users/addUser").permitAll()
                .antMatchers("/users/deleteUser/{id}").hasRole("MANAGER")
                .antMatchers("/users/updateUser/{id}").hasRole("ADMIN")
                .antMatchers("/users/findUser/{id}").hasRole("USER")
                .anyRequest().authenticated() ;

    }

}
