package com.wsourcing.Authentication.resource;


import com.auth0.jwt.JWT;


import com.wsourcing.Authentication.exception.UserNotFoundException;
import com.wsourcing.Authentication.model.User;
import com.wsourcing.Authentication.model.UserKnowage;
import com.wsourcing.Authentication.repository.UserRepository;
import com.wsourcing.Authentication.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


import java.util.*;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class PublicRestApiController {

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    @Autowired
    static UserRepository userRepository;


    private boolean foundUser = false;

    private com.wsourcing.Authentication.security.JwtAuthorizationFilter JwtAuthorizationFilter;

    private Map<String, String> map= new HashMap<>();

    public PublicRestApiController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User findByUsername(String userName){


        User user = new User() ;
        List<User> l = userRepository.findAll();
        for (int i = 0; i < l.size(); i++) {

            if(l.get(i).getUsername().equals(userName)){
                user=l.get(i);
            }
        }
        if(user!=null){
            return user ;
        }
        else{
return null ;
        }


    }

    @PostMapping("/authBackround")
    public String tokenValid(@RequestBody UserKnowage userKnowage) {
boolean valid = false ;
        List<User> l = userRepository.findAll();

       // String username = map.get("username");
       // String pwd = findByUsername(username).getPassword();


       // if (userKnowage.getUsername().equals(username)&& userKnowage.getPassword().equals(pwd) ) {

            for (int i = 0; i < l.size(); i++) {
                if ((userKnowage.getUsername().equals(l.get(i).getUsername())) && (userKnowage.getPassword().equals(l.get(i).getPassword()))) {

                    valid = true ;
                    //foundUser = true;
                }
            }
       // }

        if (valid){
            foundUser = true;
            return "Identifiers correct! you can process your authentication!";

        } else{
            foundUser = false;
            return "username or password not the same or incorrect ";
    }
    }
    @PostMapping("/auth")
    public Map<String, String> GenerateToken(@RequestBody User user) throws UserNotFoundException {

        List<User> l = userRepository.findAll();
        //HashMap<String, String> map = new HashMap<>();

        if (foundUser) {
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


                    long id = l.get(i).getId();
                    String idString = Long.toString(id);

                    map.put("username", principal.getUsername());
                    map.put("role", "ROLE_" + l.get(i).getRoles());
                    map.put("token", token);
                    map.put("user_id", idString);

                }
            }
            foundUser=false ;
            return map;
        }else {
            foundUser=false ;
            throw new UserNotFoundException("Is this a valid identifiers ?") ;
            }
        }

            }
         /*   //return null ;
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
            if (username == false | password == false) {
                throw new UserNotFoundException("username or password incorrect");
            } else {
                foundUser=false ;
                return map;
            }*/


/*
   // @GetMapping("/{URL_Knowage_Server}/knowage/servlet/AdapterHTTP?PAGE=LoginPage&NEW_SESSION=TRUE&doc={Doc_Name}&token={Token}&user_id={User_ID}")
    @GetMapping("/{URL_Knowage_Server}/{Doc_Name}/{Token}/{User_ID}")
    public RedirectView loginPage(@PathVariable String URL_Knowage_Server, @PathVariable String Doc_Name, @PathVariable String Token, @PathVariable int User_ID)throws UserNotFoundException{

        User user= userRepository.findById(User_ID) ;
        Boolean valideToken = JWT.decode(Token).equals(GenerateToken(user).get("token")) ;

        if(valideToken == false){
            throw new UserNotFoundException("The token is invalid don't exist") ;
        }
        else{
        //    model.addAttribute("attribute", "redirectWithRedirectPrefix");

          //  return new ModelAndView("redirect:" +"http://localhost:"+URL_Knowage_Server+"/knowage/servlet/AdapterHTTP?PAGE=LoginPage" +
                   // "&NEW_SESSION=TRUE&doc="+Doc_Name+"&token="+Token+"&user_id="+User_ID) ;
            //return user ;
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("http://"+URL_Knowage_Server+"/knowage/servlet/AdapterHTTP?PAGE=LoginPage" +
                    "&NEW_SESSION=TRUE&doc="+Doc_Name+"&token="+Token+"&user_id="+User_ID);
            return redirectView;

        }
        //   return new ModelAndView("login");

    }




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



