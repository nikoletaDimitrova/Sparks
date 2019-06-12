package com.sap.sparks.controllers;

import com.sap.sparks.entity.Roles;
import com.sap.sparks.entity.Users;
import com.sap.sparks.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/users")
public class UsersController {

    private UsersService usersService;


    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/findAllUsers")
    public ResponseEntity<List<Users>> findAll() {
        List<Users> allUsers = usersService.findAll();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Users> getUsers(@PathVariable int id) {

        Users user = usersService.findById(id);
        if (user == null) {
            throw new RuntimeException("User id not found - " + id);
        }
       return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PostMapping(value = "/addUsers", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Users> addUser(@RequestBody Users user) {
        Users userExists = usersService.findByUserName(user.getUserName());
        usersService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/updateUser/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Users> updateUser(@PathVariable("id") Integer id, @RequestBody Users user){
        Users existingUser = usersService.findById(id);
        if(existingUser == null){
            throw new RuntimeException("User id not found - " + id);
        }
        user.setId(id);
        usersService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {

        Users tempUser = usersService.findById(id);

        if (tempUser == null) {
            throw new RuntimeException("User id not found - " + id);
        }

        usersService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/findByUsername/{userName}")
    public ResponseEntity<Users> getByUsername(@PathVariable("userName") String userName){
        Users user = usersService.findByUserName(userName);
        if(user == null){
            throw new RuntimeException("User with username " + userName + "doesn't exist");
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("findRolesOfUser/{userName}")
    public ResponseEntity<List<Roles>> getRolesForUser(@PathVariable("userName") String userName){
        List<Roles> roles = usersService.findRolesOfUser(userName);
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

//    public ResponseEntity<Void> addStaff()
}