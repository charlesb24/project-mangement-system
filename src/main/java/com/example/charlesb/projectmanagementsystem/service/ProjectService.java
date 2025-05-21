package com.example.charlesb.projectmanagementsystem.service;

import com.example.charlesb.projectmanagementsystem.entity.Project;
import com.example.charlesb.projectmanagementsystem.entity.User;

import java.util.List;

public interface ProjectService {

    List<Project> findAll();
    List<Project> findAllAssignedToUser(User user);
    List<Project> findAllCreatedByUser(User user);
    List<Project> findAllInProgressByUser(User user);

    Project findById(long projectId);

    void save(Project project);

    void deleteById(long projectId);

}
