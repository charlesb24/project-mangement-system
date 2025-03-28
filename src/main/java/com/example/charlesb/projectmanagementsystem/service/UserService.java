package com.example.charlesb.projectmanagementsystem.service;

import com.example.charlesb.projectmanagementsystem.dto.UserDTO;
import com.example.charlesb.projectmanagementsystem.entity.User;

import java.util.List;

public interface UserService {

    void saveUser(UserDTO userDTO);

    void updateUser(User user);

    User findUserById(Long id);
    User findUserByEmail(String email);

    List<UserDTO> findAllUsers();

}
