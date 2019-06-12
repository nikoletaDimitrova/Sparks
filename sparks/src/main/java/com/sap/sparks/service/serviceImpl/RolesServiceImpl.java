package com.sap.sparks.service.serviceImpl;

import com.sap.sparks.entity.Roles;
import com.sap.sparks.repositories.RolesRepository;
import com.sap.sparks.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RolesServiceImpl implements RolesService {

    private RolesRepository rolesRepository;

    @Autowired
    public RolesServiceImpl(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public List<Roles> findAll() {
        return rolesRepository.findAll();
    }

    @Override
    public Roles findById(int id) {
        Optional<Roles> result = rolesRepository.findById(id);

        Roles role= null;

        if (result.isPresent()) {
            role = result.get();
        } else {
            throw new RuntimeException("Did not find boss id - " + id);
        }

        return role;
    }

    @Override
    public void save(Roles role) {
        rolesRepository.save(role);
    }

    @Override
    public void deleteById(int id) {
        rolesRepository.deleteById(id);
    }

}


