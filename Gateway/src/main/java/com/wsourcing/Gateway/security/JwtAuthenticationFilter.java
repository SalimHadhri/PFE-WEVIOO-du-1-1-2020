package com.wsourcing.Gateway.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wsourcing.Gateway.model.LoginViewModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //we want to modify the behavior when we receive the login request and we want to attempt authentication

    /* Trigger when we issue (émettons) POST request to /login
 We also need to pass in {"username":"dan", "password":"dan123"} in the request body
  */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {



        // Grab credentials and map them to login viewmodel
        LoginViewModel credentials = null;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), LoginViewModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create login token
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                credentials.getPassword(),
                new ArrayList<>());

        // Authenticate user
        Authentication auth = authenticationManager.authenticate(authenticationToken);

        return auth;
    }
    //


    //if the authentication is success we want to build the jwt token

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // Grab principal
        //done via the database
        com.wsourcing.Gateway.security.UserPrincipal principal = (com.wsourcing.Gateway.security.UserPrincipal) authResult.getPrincipal();

        // Create JWT Token
        //HMAC512: algorithm to sign with
               String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + com.wsourcing.Gateway.security.JwtProperties.EXPIRATION_TIME)) //EXPIRATION_TIME : property created earlier
                .sign(HMAC512(com.wsourcing.Gateway.security.JwtProperties.SECRET.getBytes())); // SECRET : stored in JWT properties

        // Add token in response

        //HEADER_STRING : authorization header
        //TOKEN_PREFIX : Bearer
        response.addHeader(com.wsourcing.Gateway.security.JwtProperties.HEADER_STRING, com.wsourcing.Gateway.security.JwtProperties.TOKEN_PREFIX + token);
        // response back to the user
    }

    //the rest will be handled by spring security
}
