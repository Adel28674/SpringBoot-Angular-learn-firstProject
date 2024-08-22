package com.example.firstProject.Service;

import com.example.firstProject.Interface.BookRepository;
import com.example.firstProject.Interface.UserRepository;
import com.example.firstProject.Model.BookEntity;
import com.example.firstProject.Model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;


    public UserEntity getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(()->new RuntimeException(userId  +  " not found"));
    }

    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }


    public void addUser(UserEntity userEntity){
        userRepository.save(userEntity);
    }


    public  void addUsers(List<UserEntity> listUsers){
        userRepository.saveAll(listUsers);
    }

    public void deleteUserById(Long userId){
        userRepository.deleteById(userId);
    }

    public void deleteUser(UserEntity userEntity){
        userRepository.delete(userEntity);
    }

    public void deleteUsers(List<Long> listUsers){
        userRepository.deleteAllById(listUsers);
    }

    public void deleteAll(){
        userRepository.deleteAll();
    }



}
