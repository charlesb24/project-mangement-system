package com.example.charlesb.projectmanagementsystem.dao;

import com.example.charlesb.projectmanagementsystem.entity.Requirement;
import com.example.charlesb.projectmanagementsystem.entity.User;
import com.example.charlesb.projectmanagementsystem.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequirementRepository extends JpaRepository<Requirement, Long> {

    List<Requirement> findAllByAssignedToAndStatus(User user, Status status);

}
