package com.example.charlesb.projectmanagementsystem.dao;

import com.example.charlesb.projectmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    List<User> findAllByManagerId(long id);

}
