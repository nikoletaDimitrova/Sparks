package com.sap.sparks.service;

import com.sap.sparks.entity.Roles;
import com.sap.sparks.entity.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsersService extends UserDetailsService
{

    List<Users> findAll();

    Users findById(int id);

    void save(Users user);

    void deleteById(int id);

    Users findByUserName(String userName);

    List<Roles> findRolesOfUser(String userName);


}


