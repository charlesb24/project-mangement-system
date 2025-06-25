package com.example.charlesb.projectmanagementsystem.service;

import com.example.charlesb.projectmanagementsystem.dto.UserDTO;
import com.example.charlesb.projectmanagementsystem.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    void saveUser(UserDTO userDTO);

    void updateUser(User user);

    void deleteUser(Long id);

    boolean phoneExists(String phone);

    User findUserById(Long id);
    User findUserByEmail(String email);

    List<UserDTO> findAllUsers();
    List<UserDTO> findManagers();
    List<UserDTO> findAssignableUsers(UserDetails userDetails);

    User mapToUser(UserDTO userDTO);
    UserDTO mapToDTO(User user);

}
