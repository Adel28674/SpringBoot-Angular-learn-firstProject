package com.example.firstProject.Controller;

import com.example.firstProject.Model.BookEntity;
import com.example.firstProject.Model.UserEntity;
import com.example.firstProject.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(){
        return ResponseEntity.ok("Welcome");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody UserEntity userEntity){
        userService.addUser(userEntity);
        return ResponseEntity.ok("the user " + userEntity.getUsername() + " have been added");
    }

    @PostMapping("/addUsers")
    public ResponseEntity<String> addUsers(@RequestBody List<UserEntity> usersEntity){
        userService.addUsers(usersEntity);
        return ResponseEntity.ok("the users have been added");
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId){
        userService.deleteUserById(userId);
        return ResponseEntity.ok(userId + " has been deleted");
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestBody UserEntity userEntity){
        userService.deleteUser(userEntity);
        return ResponseEntity.ok(userEntity.getUsername() + " has been deleted");
    }

    @DeleteMapping("/deleteUsers")
    public ResponseEntity<String> deleteUsers(@RequestBody List<Long> userIds){
        userService.deleteUsers(userIds);
        return ResponseEntity.ok("");
    }


    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleterAllUser(){
        userService.deleteAll();
        return ResponseEntity.ok("");
    }




}
