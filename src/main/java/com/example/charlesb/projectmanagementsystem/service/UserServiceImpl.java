package com.example.charlesb.projectmanagementsystem.service;

import com.example.charlesb.projectmanagementsystem.dao.RoleRepository;
import com.example.charlesb.projectmanagementsystem.dao.UserRepository;
import com.example.charlesb.projectmanagementsystem.dto.UserDTO;
import com.example.charlesb.projectmanagementsystem.entity.Role;
import com.example.charlesb.projectmanagementsystem.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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
        User user = mapToUser(userDTO);

        if (userDTO.getManagerId() != null) {
            Optional<User> manager = userRepository.findById(userDTO.getManagerId());
            manager.ifPresent(value -> user.setManagerId(value.getId()));
        }

        if (user.getRoles().isEmpty()) {
            Role defaultRole = generateOrFindRole("ROLE_USER");
            user.setRoles(Set.of(defaultRole));
        }

        if (userRepository.findAll().isEmpty()) {
            Role ownerRole = generateOrFindRole("ROLE_OWNER");
            user.getRoles().add(ownerRole);
        }

        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> mapToDTO(user)).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findManagers() {
        List<User> managers = userRepository.findAllWithRole("ROLE_MANAGER");

        return managers.stream().map(manager -> mapToDTO(manager)).toList();
    }

    @Override
    public List<UserDTO> findAssignableUsers(UserDetails userDetails) {
        User assigningUser = userRepository.findByEmail(userDetails.getUsername());
        List<User> assignableUsers = userRepository.findAllByManagerId(assigningUser.getId());

        assignableUsers.add(assigningUser);

        return assignableUsers.stream().map(user -> mapToDTO(user)).toList();
    }

    @Override
    public UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();

        if (user == null) {
            return userDTO;
        }

        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setMiddleName(user.getMiddleName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPhone(user.getPhone());
        userDTO.setContactMethod(user.getContactMethod());
        userDTO.setManagerId(user.getManagerId());

        return userDTO;
    }

    @Override
    public User mapToUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId()).orElse(new User());

        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        user.setFirstName(userDTO.getFirstName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setLastName(userDTO.getLastName());
        user.setPhone(userDTO.getPhone());
        user.setContactMethod(userDTO.getContactMethod());
        user.setManagerId(userDTO.getManagerId());

        return user;
    }

    private Role generateOrFindRole(String roleName) {
        Role role = roleRepository.findByName(roleName);

        if (role == null) {
            role = new Role();
            role.setName(roleName);
            return roleRepository.save(role);
        }

        return role;
    }

}
