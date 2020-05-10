package com.wsourcing.Services.security;


import com.wsourcing.Services.Accounts.resource.AccountController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableScheduling
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtProperties config;



   @Autowired
    private AccountController accountController ;

    //@Bean
    //public AccountController Account() {
    //    return new AccountController();
   // }

    @Bean
    public JwtProperties jwtConfig() {
        return new JwtProperties();
    }


    private List<Integer> ScrapingDays= new ArrayList<>() ;






    @Scheduled(cron = "1 * * * * ?",zone="Africa/Tunis")
    public void ScrapEveryDay() {
       // AccountController accountController=new AccountController() ;
        int newScrapedProfiles = accountController.nbrScrapingDone();


        if(ScrapingDays.size()<7) {
              ScrapingDays.add(newScrapedProfiles);
          }
          else{
              ScrapingDays= new ArrayList<>();
            ScrapingDays.add(newScrapedProfiles);
        }

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
                //accounts
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
                .antMatchers("/accounts/ScrapThiDay").permitAll()

                //searches
                .antMatchers(HttpMethod.POST,"/searches/**").permitAll()
                .antMatchers(HttpMethod.GET,"/searches/**").permitAll()
                .antMatchers(HttpMethod.DELETE,"/searches/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/searches/**").permitAll()
                .antMatchers("/searches/listSearches").permitAll()
                .antMatchers("/searches/addSearch").hasRole("ADMIN")
                .antMatchers("/searches/deleteSearch/{id}").hasAnyRole("ADMIN","MANAGER")
                .antMatchers("/searches/updateSearch/{id}").hasRole("ADMIN")
                .antMatchers("/searches/findSearch/{id}").hasRole("USER")
                .antMatchers("/searches/StartOrStopSearch/{id}").hasRole("ADMIN")
                .antMatchers("/searches/findPrioritisedSearch/{organisme}").hasRole("ADMIN")
                //profiles
                .antMatchers(HttpMethod.POST,"/profiles/**").permitAll()
                .antMatchers(HttpMethod.GET,"/profiles/**").permitAll()
                .antMatchers(HttpMethod.DELETE,"/profiles/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/profiles/**").permitAll()
                .antMatchers("/profiles/addProfile").hasRole("ADMIN")
                .antMatchers("/profiles/listProfiles").permitAll()
                .antMatchers("/profiles/updateProfile/{id}").hasRole("ADMIN")
                .antMatchers("/profiles/deleteProfile/{id}").hasRole("ADMIN")
                .antMatchers("/profiles/findProfile/{id}").hasRole("ADMIN")
                ///Searches
                .antMatchers("/profiles/SearchAll/{min}/{max}/{tunisia}/{categorie}/{profile}/{termes}").permitAll()
                .antMatchers("/profiles/SearchName/{name}").hasRole("USER")
                .antMatchers("/profiles/SearchName/{name}").hasRole("USER")
                .antMatchers("/profiles/SearchSimilarProfiles/{profile}/{termes}").hasRole("USER")




                .anyRequest().authenticated() ;
    }

    public List<Integer> getScrapingDays() {
        return ScrapingDays;
    }

    public void setScrapingDays(List<Integer> scrapingDays) {
        ScrapingDays = scrapingDays;
    }
}
