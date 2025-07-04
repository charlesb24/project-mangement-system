package com.example.charlesb.projectmanagementsystem.dao;

import com.example.charlesb.projectmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Optional<User> findByPhone(String phone);

    List<User> findAllByManagerId(long id);

    @Query("select u from User u inner join u.roles r where r.name = :role")
    List<User> findAllWithRole(@Param("role") String role);

}
