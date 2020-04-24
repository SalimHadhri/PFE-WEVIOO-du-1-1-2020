package com.wsourcing.Authentication.resource;


import com.auth0.jwt.JWT;


import com.wsourcing.Authentication.exception.UserNotFoundException;
import com.wsourcing.Authentication.model.User;
import com.wsourcing.Authentication.repository.UserRepository;
import com.wsourcing.Authentication.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.*;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class PublicRestApiController {

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder ;

    @Autowired
    private UserRepository userRepository;
    private com.wsourcing.Authentication.security.JwtAuthorizationFilter JwtAuthorizationFilter;


    public PublicRestApiController(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @PostMapping("/auth")
    public Map<String, String> GenerateToken (@RequestBody User user) throws UserNotFoundException {

        List<User> l = userRepository.findAll();


        for (int i = 0; i < l.size(); i++) {
            if ((user.getUsername().equals(l.get(i).getUsername())) && (user.getPassword().equals(l.get(i).getPassword()))) {
                UserPrincipal principal = new UserPrincipal(user);

                //System.out.println(l.get(i));

                String token = JWT.create()
                        .withSubject(principal.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + com.wsourcing.Authentication.security.JwtProperties.EXPIRATION_TIME)) //EXPIRATION_TIME : property created earlier
                        .sign(HMAC512(com.wsourcing.Authentication.security.JwtProperties.SECRET.getBytes()));

                String auth = "";

                for (Iterator<? extends GrantedAuthority> iter = principal.getAuthorities().iterator(); iter.hasNext(); ) {
                    GrantedAuthority element = iter.next();
                    auth = auth + element.getAuthority();
                }


                HashMap<String, String> map = new HashMap<>();

                map.put("username", principal.getUsername());
                map.put("role", "ROLE_" + l.get(i).getRoles());
                map.put("token", token);
                return map;

            }
        }
        //return null ;
        boolean username = false;
        boolean password = false;
        for (int i = 0; i < l.size(); i++) {
            if (user.getUsername() == l.get(i).getUsername()) {
                username = true;
            }
            if (user.getPassword() == l.get(i).getPassword()) {
                password = true;
            }

        }
        if(username==false | password==false){
        throw new UserNotFoundException("username or password incorrect");
    }
        return null ;
    }
/*
    // Available to all authenticated users
    @GetMapping("test")
    public String test1(){
        return "API Test";
    }

    // Available to managers
    @GetMapping("management/reports")
    public String reports(){

        return "Some report data";
    }

    // Available to ROLE_ADMIN
    @GetMapping("admin/users")
    public List<User> users(){
        return this.userRepository.findAll();
    }

/*
    @GetMapping("/auth")
    public String GenerateToken (@RequestBody User user){


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>());

        Authentication auth = authenticationManager.authenticate(authenticationToken);

        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();

        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + com.wsourcing.microserviceusers.security.JwtProperties.EXPIRATION_TIME)) //EXPIRATION_TIME : property created earlier
                .sign(HMAC512(com.wsourcing.microserviceusers.security.JwtProperties.SECRET.getBytes())); // SECRET : stored in JWT properties


        return token ;
    }*/



}