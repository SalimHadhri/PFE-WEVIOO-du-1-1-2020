package com.wsourcing.Gateway.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wsourcing.Gateway.model.User;
import com.wsourcing.Gateway.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    //Use it to extract our UserDetails based on the username that we read from the token that we are going to receive
    //like authenticationFilter we need to extend some functionality to make authorization work for us
    // dataset to have access to the authorization data
    private UserRepository userRepository ;



    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint, UserRepository userRepository) {
        super(authenticationManager, authenticationEntryPoint);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Read the Authorization header, where the JWT token should be
        String header = request.getHeader(com.wsourcing.Gateway.security.JwtProperties.HEADER_STRING);

        // If header does not contain BEARER or is null delegate to Spring impl and exit
        if (header == null || !header.startsWith(com.wsourcing.Gateway.security.JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        // If header is present, try grab user principal from database and perform authorization
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continue filter execution
        chain.doFilter(request, response);
    }

   //get our authentication object based on our request
   public Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(com.wsourcing.Gateway.security.JwtProperties.HEADER_STRING)
                .replace(com.wsourcing.Gateway.security.JwtProperties.TOKEN_PREFIX,"");

       // parse the token and validate it
       String userName = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()))
               .build()
               .verify(token)
               .getSubject(); //the username

       // Search in the DB if we find the user by token subject (username)
       // If so, then grab user details and create spring auth token using username, pass, authorities/roles
       if (userName != null) {
           User user = userRepository.findByUsername(userName);
           UserPrincipal principal = new UserPrincipal(user) ;
           //internal spring security username password authentication token
           UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userName, null, principal.getAuthorities());
           return auth;
       }
       return null;
   }

}
