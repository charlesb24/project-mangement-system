package com.example.charlesb.projectmanagementsystem.service;

import com.example.charlesb.projectmanagementsystem.dao.RoleRepository;
import com.example.charlesb.projectmanagementsystem.dao.UserRepository;
import com.example.charlesb.projectmanagementsystem.dto.UserDTO;
import com.example.charlesb.projectmanagementsystem.entity.Role;
import com.example.charlesb.projectmanagementsystem.entity.User;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
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
    private final SessionRegistry sessionRegistry;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, SessionRegistry sessionRegistry) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.sessionRegistry = sessionRegistry;
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

        reloadUserByEmail(userDTO.getEmail());
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean phoneExists(String phone) {
        return userRepository.findByPhone(phone).isPresent();
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
    public List<UserDTO> findManagedUsers(UserDetails userDetails) {
        User manager = userRepository.findByEmail(userDetails.getUsername());

        return userRepository.findAllByManagerId(manager.getId()).stream().map(user -> mapToDTO(user)).toList();
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

        userDTO.setLocked(user.isLocked());
        userDTO.setEnabled(user.isEnabled());

        Set<String> roleNames = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());

        userDTO.setRoles(roleNames);

        return userDTO;
    }

    @Override
    public User mapToUser(UserDTO userDTO) {
        User user;

        if (userDTO.getId() != null) {
            user = userRepository.findById(userDTO.getId()).orElse(new User());
        } else {
            user = new User();
        }

        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        user.setFirstName(userDTO.getFirstName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setLastName(userDTO.getLastName());
        user.setContactMethod(userDTO.getContactMethod());
        user.setManagerId(userDTO.getManagerId());

        user.setLocked(userDTO.isLocked());
        user.setEnabled(userDTO.isEnabled());

        if (userDTO.getPhone().isEmpty()) {
            user.setPhone(null);
        }

        return user;
    }

    @Override
    public void reloadUserByEmail(String email) {
        org.springframework.security.core.userdetails.User targetUser = sessionRegistry.getAllPrincipals().stream().map(u -> (org.springframework.security.core.userdetails.User) u).filter(u -> u.getUsername().equals(email)).findFirst().orElse(null);

        if (targetUser != null) {
            List<SessionInformation> currentSessions = sessionRegistry.getAllSessions(targetUser, false);

            if (!currentSessions.isEmpty()) {
                currentSessions.forEach(session -> session.expireNow());
            }
        }

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
