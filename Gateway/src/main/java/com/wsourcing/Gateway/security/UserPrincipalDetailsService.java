package com.wsourcing.Gateway.security;


import com.wsourcing.Gateway.model.User;
import com.wsourcing.Gateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


//UserPrincipalDetailsService extract user from an xl file /database/in memory
//convert user to userPrincipal that implements the userDetails class
//@service ===>>> enable this component to be eligible for auto wiring
@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository ;

    public UserPrincipalDetailsService() {
    }

    public UserPrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = this.userRepository.findByUsername(s) ;
        UserPrincipal userPrincipal = new UserPrincipal(user) ;


        return userPrincipal;
    }
}
