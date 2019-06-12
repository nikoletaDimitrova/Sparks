package com.sap.sparks.repositories;

import com.sap.sparks.entity.RoleName;
import com.sap.sparks.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {
//    Roles findByTypeUser(String typeUser);
Roles findByTypeUser(String roleName);
}
