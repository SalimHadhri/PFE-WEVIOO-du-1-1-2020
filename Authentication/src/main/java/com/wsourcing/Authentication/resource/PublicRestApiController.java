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

// Controller related to our authentication process
@RestController
@RequestMapping("/users")
@CrossOrigin
public class PublicRestApiController {

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    private com.wsourcing.Authentication.security.JwtAuthorizationFilter JwtAuthorizationFilter;

    public PublicRestApiController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Get USER credentials
    //return a map with the username, role, token and user_id related to this USER
    //return an exception in case of non existing USER with these credentials
    @PostMapping("/auth")
    public Map<String, String> GenerateToken(@RequestBody User user) throws UserNotFoundException {

        List<User> l = userRepository.findAll();

        for (int i = 0; i < l.size(); i++) {
            if ((user.getUsername().equals(l.get(i).getUsername())) && (user.getPassword().equals(l.get(i).getPassword()))) {
                UserPrincipal principal = new UserPrincipal(user);
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

                long id = l.get(i).getId();
                String idString = Long.toString(id);

                map.put("username", principal.getUsername());
                map.put("role", "ROLE_" + l.get(i).getRoles());
                map.put("token", token);
                map.put("user_id", idString);
                return map;
            }
        }

        boolean username = false;
        boolean password = false;

        for (int i = 0; i < l.size(); i++) {
            if (user.getUsername().equals(l.get(i).getUsername())) {
                username = true;
            }
            if (user.getPassword().equals(l.get(i).getPassword())) {
                password = true;
            }

        }
        // In case of non existing USER with the username and password credentials fulfilled in the form
        if (!username | !password) {
            throw new UserNotFoundException("username or password incorrect");
        }
        return null;
    }

}