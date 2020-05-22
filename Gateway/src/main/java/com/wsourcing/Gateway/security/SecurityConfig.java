package com.wsourcing.Gateway.security;


import com.wsourcing.Gateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;


/**
 * Config role-based auth.
 *
 * @author shuaicj 2017/10/18
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserPrincipalDetailsService userPrincipalDetailsService ;
    @Autowired
    private UserRepository userRepository;
    //private BasicAuthenticationEntryPoint authenticationEntryPoint ;
    //=> we don't have multiple security runs in here  */

   // @Autowired
    public void SecurityConfiguration(  UserPrincipalDetailsService userPrincipalDetailsService, UserRepository userRepository) {
        this.userPrincipalDetailsService = userPrincipalDetailsService; //injected directly into the security configuration
        this.userRepository = userRepository;
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

/*creating 2 users:  admin user/ different user call Dann
        provide them username and password
        configure http basic for all the request
     */

    //define a data sources to our users
    //database provider that we used previously



    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider()) ;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .logout().disable()
                .formLogin().disable()
               // .logout().disable()
               // .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // make sure we use stateless session; session won't be used to store user's state.
                .anonymous()
                .and()
                // handle an authorized attempts
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(),this.userRepository))
                .authorizeRequests()
                //authentication
                .antMatchers("/user/users/auth").permitAll()
                //accounts
                .antMatchers("/service/accounts/listAccounts").permitAll()
                .antMatchers("/service/accounts/addAccount").permitAll()
                .antMatchers("/service/accounts/deleteAccount/{id}").permitAll()
                .antMatchers("/service/accounts/updateAccount/{id}").permitAll()
                .antMatchers("/service/accounts/findAccount/{id}").permitAll()
                .antMatchers("/service/accounts/minAccount").permitAll()
                .antMatchers("/service/accounts/nbrScrapingDone").permitAll()
                .antMatchers("/service/accounts/nbrAccountsInWork").permitAll()
                .antMatchers("/service/accounts/orderedNbrScrapingAccounts/{min}/{max}").permitAll()
                .antMatchers("/service/accounts/updateStatus/{id}").permitAll()
                .antMatchers("/service/accounts/ScrapThiDay").permitAll()
                .antMatchers("/service/accounts/ExpiredLiat/{id}").permitAll()
                .antMatchers("/service/accounts/liatToExpired/{id}").permitAll()
                //users
                .antMatchers("/user/users/listUsers").permitAll()
                .antMatchers("/user/users/addUser").permitAll()
                .antMatchers("/user/users/deleteUser/{id}").permitAll()
                .antMatchers("/user/users/updateUser/{id}").permitAll()
                .antMatchers("/user/users/findUser/{id}").permitAll()
                //searches
                .antMatchers("/service/searches/listSearches").permitAll()
                .antMatchers("/service/searches/addSearch").permitAll()
                .antMatchers("/service/searches/deleteSearch/{id}").permitAll()
                .antMatchers("/service/searches/updateSearch/{id}").permitAll()
                .antMatchers("/service/searches/findSearch/{id}").permitAll()
                .antMatchers("/service/searches/StartOrStopSearch/{id}").permitAll()
                .antMatchers("/service/searches/StartOrStopSearch/{id}").hasRole("ADMIN")
                .antMatchers("/service/searches/findPrioritisedSearch/{organisme}").hasRole("ADMIN")
                //profiles
                .antMatchers("/service/profiles/addProfile").hasRole("ADMIN")
                .antMatchers("/service/profiles/listProfiles").permitAll()
                .antMatchers("/service/profiles/findProfile/{id}").hasRole("ADMIN")
                .antMatchers("/service/profiles/updateProfile/{id}").hasRole("ADMIN")
                .antMatchers("/service/profiles/deleteProfile/{id}").hasRole("ADMIN")
                //searches
                .antMatchers("/service/profiles/SearchAll/{min}/{max}/{tunisia}/{categorie}/{profile}/{termes}").permitAll()
                .antMatchers("/service/profiles/SearchName/{name}").permitAll()
                .antMatchers("/service/profiles/SearchSimilarProfiles/{profile}/{termes}").hasRole("USER")





        ;

    }
    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider()  ;
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);

        return daoAuthenticationProvider ;
    }

    public UserPrincipalDetailsService getUserPrincipalDetailsService() {
        return userPrincipalDetailsService;
    }

    @Autowired
    public void setUserPrincipalDetailsService(UserPrincipalDetailsService userPrincipalDetailsService) {
        this.userPrincipalDetailsService = userPrincipalDetailsService;
    }


     @Bean
     PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder(); // or any other compatible encoder
        return encoder;
    }
}

