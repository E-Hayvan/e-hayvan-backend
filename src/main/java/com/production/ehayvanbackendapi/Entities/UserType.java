package com.production.ehayvanbackendapi.Entities;

import jakarta.persistence.*;
import org.apache.catalina.User;

import java.util.List;

@Entity
@Table(name = "UserType")
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer UserTypeID;
    @Column(nullable = false)
    private String Type;
    @OneToMany(mappedBy = "UserTypeID")
    private List<Customer> Users;
    public UserType(){

    }
    public UserType(int type){
        UserTypeID = type;
    }
    public int getUserTypeID() {
        return UserTypeID;
    }
    public void setUserTypeID(int userTypeID) {
        UserTypeID = userTypeID;
    }
    public String getType() {
        return Type;
    }
    public void setType(String type) {
        Type = type;
    }
    public List<Customer> getUsers() {
        return Users;
    }
    public void setUsers(List<Customer> users) {
        Users = users;
    }
}
