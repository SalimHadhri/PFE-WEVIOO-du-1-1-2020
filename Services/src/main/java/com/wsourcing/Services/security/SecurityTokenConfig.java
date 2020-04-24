package com.wsourcing.Services.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtProperties config;

    @Bean
    public JwtProperties jwtConfig() {
        return new JwtProperties();
    }



    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .logout().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .anonymous()
                .and()
                .exceptionHandling().authenticationEntryPoint(
                (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterAfter(new JwtTokenAuthenticationFilter(config),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/accounts/**").permitAll()
                .antMatchers(HttpMethod.GET,"/accounts/**").permitAll()
                .antMatchers(HttpMethod.DELETE,"/accounts/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/accounts/**").permitAll()
                .antMatchers("/accounts/listAccounts").permitAll()
                .antMatchers("/accounts/addAccount").hasRole("ADMIN")
                .antMatchers("/accounts/deleteAccount/{id}").hasAnyRole("ADMIN","MANAGER")
                .antMatchers("/accounts/updateAccount/{id}").hasRole("ADMIN")
                .antMatchers("/accounts/findAccount/{id}").hasRole("USER")
                .antMatchers("/accounts/minAccount").permitAll()
                .antMatchers("/accounts/nbrScrapingDone").hasRole("MANAGER")
                .antMatchers("/accounts/nbrAccountsInWork").permitAll()
                .antMatchers("/accounts/orderedNbrScrapingAccounts/{min}/{max}").hasRole("ADMIN")
                .antMatchers("/accounts/updateStatus/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/searches/**").permitAll()
                .antMatchers(HttpMethod.GET,"/searches/**").permitAll()
                .antMatchers(HttpMethod.DELETE,"/searches/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/searches/**").permitAll()
                .antMatchers("/searches/listSearches").permitAll()
                .antMatchers("/searches/addSearch").hasRole("ADMIN")
                .antMatchers("/searches/deleteSearch/{id}").hasAnyRole("ADMIN","MANAGER")
                .antMatchers("/searches/updateSearch/{id}").hasRole("ADMIN")
                .antMatchers("/searches/findSearch/{id}").hasRole("USER")
                .anyRequest().authenticated() ;
    }


}