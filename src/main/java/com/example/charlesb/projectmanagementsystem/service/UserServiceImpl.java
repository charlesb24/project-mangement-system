package com.example.charlesb.projectmanagementsystem.service;

import com.example.charlesb.projectmanagementsystem.dao.RoleRepository;
import com.example.charlesb.projectmanagementsystem.dao.UserRepository;
import com.example.charlesb.projectmanagementsystem.dto.UserDTO;
import com.example.charlesb.projectmanagementsystem.entity.Role;
import com.example.charlesb.projectmanagementsystem.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        user.setFirstName(userDTO.getFirstName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setLastName(userDTO.getLastName());

        user.setWorkEmail(userDTO.getWorkEmail());
        user.setWorkPhone(userDTO.getWorkPhone());

        user.setPersonalEmail(userDTO.getPersonalEmail());
        user.setMobilePhone(userDTO.getMobilePhone());

        Optional<User> manager = userRepository.findById(userDTO.getManagerId());
        manager.ifPresent(value -> user.setManagerId(value.getId()));

        Role defaultRole = roleRepository.findByName("ROLE_USER");

        if (defaultRole == null) {
            defaultRole = generateDefaultRole();
        }

        user.setRoles(List.of(defaultRole));

        userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> mapToUserDTO(user)).collect(Collectors.toList());
    }

    private UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setMiddleName(user.getMiddleName());
        userDTO.setLastName(user.getLastName());
        userDTO.setWorkEmail(user.getWorkEmail());
        userDTO.setWorkPhone(user.getWorkPhone());
        userDTO.setPersonalEmail(user.getPersonalEmail());
        userDTO.setMobilePhone(user.getMobilePhone());

        return userDTO;
    }

    private Role generateDefaultRole() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }

}
