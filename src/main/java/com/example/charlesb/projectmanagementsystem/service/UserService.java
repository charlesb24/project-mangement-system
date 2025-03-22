package com.example.charlesb.projectmanagementsystem.service;

import com.example.charlesb.projectmanagementsystem.dto.UserDTO;
import com.example.charlesb.projectmanagementsystem.entity.User;

import java.util.List;

public interface UserService {

    void saveUser(UserDTO userDTO);

    User findUserByUsername(String username);

    List<UserDTO> findAllUsers();

}
