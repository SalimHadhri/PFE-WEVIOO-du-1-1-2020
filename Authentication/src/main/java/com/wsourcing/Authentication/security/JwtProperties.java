package com.wsourcing.Authentication.security;

//properties that we will use to implement both authentication and authorization in JWT
//single class for good practice
public class JwtProperties {

    public static final String url = "/user";
    public static final String SECRET = "otherpeopledontknowit"; //by which the token will be hached
    public static final int EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer "; //"Bearer authenticationToken" how we send request after we successfully authenticate
    public static final String HEADER_STRING = "Authorization";// the header in each we send the Bearer token


}
