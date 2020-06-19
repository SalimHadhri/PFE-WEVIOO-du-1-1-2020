package com.wsourcing.Authentication.security;


import com.wsourcing.Authentication.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;




/*create custom security configuration class:
    @Configuration
    @EnableWebSecurity
    extends WebSecurityConfigurerAdapter
 */

//JWT: perform authentication using our existing mechanism defined in securityConfiguration
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {





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
                .antMatchers("/users/authBackround").permitAll()

                .antMatchers(HttpMethod.POST,"/users/**").permitAll()
                .antMatchers(HttpMethod.GET,"/users/**").permitAll()
                .antMatchers(HttpMethod.DELETE,"/users/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/users/**").permitAll()
                .antMatchers("/users/listUsers").permitAll()
                .antMatchers("/users/addUser").permitAll()
                .antMatchers("/users/deleteUser/{id}").hasAnyRole("ADMIN","MANAGER")
                .antMatchers("/users/updateUser/{id}").hasRole("ADMIN")
                .antMatchers("/users/findUser/{id}").hasRole("USER")
                .anyRequest().authenticated() ;

    }




}
