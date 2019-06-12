package com.sap.sparks.service.serviceImpl;

import com.sap.sparks.config.WebSecurityConfig;
import com.sap.sparks.entity.Roles;
import com.sap.sparks.entity.Users;
import com.sap.sparks.repositories.RolesRepository;
import com.sap.sparks.repositories.UsersRepository;
import com.sap.sparks.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
//@Transactional
public class UsersServiceImpl implements UsersService, UserDetailsService {


    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    WebSecurityConfig webSecurityConfig;

    @Autowired
    private GenericWebApplicationContext context;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    @Override
    public Users findById(int id) {
        Optional<Users> result = usersRepository.findById(id);

        Users users = null;

        if (result.isPresent()) {
            users = result.get();
        } else {
            throw new RuntimeException("Did not find user id - " + id);
        }

        return users;
    }

    @Override
    public void save(Users user) {
        BCryptPasswordEncoder passwordEncoder = webSecurityConfig.passwordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setValid(false);
       Roles userRole = rolesRepository.findByTypeUser("client");
        user.setRoles(new ArrayList<>(Arrays.asList(userRole)));
        usersRepository.save(user);
    }

    @Override
    public void deleteById(int theId) {
        usersRepository.deleteById(theId);
    }


    public Users findByUserName(String userName){
        return usersRepository.findByUserName(userName);
    }


    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<Roles> roles){
        return roles.stream()
                .map(new Function<Roles, SimpleGrantedAuthority>() {
                    @Override
                    public SimpleGrantedAuthority apply(Roles role) {
                        return new SimpleGrantedAuthority(role.getTypeUser());
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = usersRepository.findByUserName(username);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }else {

                context.registerBean("usertemp", Users.class, () -> user);

        }

        return UserPrinciple.build(user); // UserPrinciple implements UserDetails
    }

    @Override
    public List<Roles> findRolesOfUser(String userName) {
        return  usersRepository.findRolesOfUser(userName);
    }
}

