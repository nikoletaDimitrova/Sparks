package com.sap.sparks.service;

import com.sap.sparks.entity.Roles;

import java.util.List;

public interface RolesService {

    List<Roles> findAll();

    Roles findById(int id);

    void save(Roles role);

    void deleteById(int id);

}


