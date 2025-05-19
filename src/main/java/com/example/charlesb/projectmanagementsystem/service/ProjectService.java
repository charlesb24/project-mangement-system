package com.example.charlesb.projectmanagementsystem.service;

import com.example.charlesb.projectmanagementsystem.entity.Project;

import java.util.List;

public interface ProjectService {

    List<Project> findAll();
    List<Project> findAllAssignedToUser(long userId);
    List<Project> findAllCreatedByUser(long userId);
    List<Project> findInProgressByUser(long userId);

    Project findById(long projectId);

    void save(Project project);

    void deleteById(long projectId);

}
