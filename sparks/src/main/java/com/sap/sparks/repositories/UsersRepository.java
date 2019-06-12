package com.sap.sparks.repositories;

import com.sap.sparks.entity.Roles;
import com.sap.sparks.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByUserName(String userName);
    Boolean existsByUserName(String username);
    Boolean existsByEmail(String email);

    @Query("select u.roles from Users u  " +
            "where u.userName = ?1 ")
    List<Roles> findRolesOfUser(String userName);
}
