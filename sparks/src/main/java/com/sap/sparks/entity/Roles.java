package com.sap.sparks.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "typeuser")
    private String typeUser;

//    @JsonBackReference(value = "manyRel")
    @JsonIgnore
    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.ALL})
    private List<Users> users;

    public Roles()
    {
    }

    public Roles(Integer id, String typeUser, List<Users> users) {
        this.id = id;
        this.typeUser = typeUser;
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public void addUsers(Users tempUser) {
        if (users == null) {
            users = new ArrayList<>();
        }

        users.add(tempUser);
    }
}
