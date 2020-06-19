package com.wsourcing.Authentication.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Document(collection = "users")
public class User {


    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    // @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    private String username;

    private String password;

    private int active;

    @JsonProperty("roles")
    private String roles = "";

    private String permissions = "";



    public User(String username, String password, int active, String roles, String permissions) {
        this.username = username;
        this.password = password;
        this.active = active ;
        this.roles = roles;
        this.permissions = permissions;
    }

    public User(){}

    public static String getSequenceName() {
        return SEQUENCE_NAME;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", roles='" + roles + '\'' +
                ", permissions='" + permissions + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public List<String> getPermissionList(){
        if(this.permissions.length() > 0){
            return Arrays.asList(this.permissions.split( ","));
        }
        return new ArrayList<>();
    }




}
