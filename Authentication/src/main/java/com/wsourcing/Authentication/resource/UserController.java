package com.wsourcing.Authentication.resource;

import com.wsourcing.Authentication.exception.UserNotFoundException;
import com.wsourcing.Authentication.model.User;
import com.wsourcing.Authentication.repository.DatabaseSequenceRepository;
import com.wsourcing.Authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//Controller related to our User class
@CrossOrigin()
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder ;
    @Autowired
    private DatabaseSequenceRepository databaseSequenceRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Add a USER to database
    @PostMapping(value = "/addUser")
    public void addUser(@RequestBody User user) {
        userRepository.save(user);
    }

    // Show all USERs stored in our mongoDB database
    @GetMapping(value = "/listUsers")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //Find a USER from database according to his id
    @GetMapping(value = "/findUser/{id}")
    public User findUserById(@PathVariable int id) throws UserNotFoundException {
        User user = userRepository.findById(id) ;
        if (user==null) throw new UserNotFoundException("the user with id "+id+" don't exist") ;
            return user ;
    }

    //Delete a USER by his id
    @DeleteMapping(value = "/deleteUser/{id}")
    public void deleteUserById(@PathVariable int id) throws UserNotFoundException {
        User user = userRepository.findById(id) ;
        if (user==null) throw new UserNotFoundException("the user with id "+id+" don't exist") ;
        userRepository.deleteById(id) ;
    }

    // Update a USER with the id = id
    // New USER data are given on a form
    @PutMapping(value = "/updateUser/{id}")
    public void updateUser(@PathVariable int id, @RequestBody User user) throws UserNotFoundException {
        User user1 = userRepository.findById(id);
        if (user1 == null) {
            throw new UserNotFoundException("the user with id "+id+" don't exist") ;

        } else {
            user.setId(user1.getId());
            this.userRepository.save(user);
        }
    }

    // Delete all users
    @DeleteMapping(value = "/deleteAllUsers")
    public String deleteAllUsers() {
        userRepository.deleteAll();
        return "All users deleted";
    }

    // Find a USER by his username
    public User findByUsername(String userName) {
        List<User> users = userRepository.findAll() ;

        for (int i = 0 ; i<users.size(); i++){
            if(users.get(i).getUsername().equals(userName)){
                return users.get(i) ;
            }
        }
        return null ;

    }

    //Delete a USER by his username
    public void deleteByUsername(String username) {
        List<User> users = userRepository.findAll() ;

        for (int i = 0 ; i<users.size(); i++){
            if(users.get(i).getUsername().equals(username)){
                {
                    int id = (int) users.get(i).getId();
                    userRepository.deleteById(id) ;
                }
            }
        }
    }
}
