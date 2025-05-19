package com.example.charlesb.projectmanagementsystem.service;

import com.example.charlesb.projectmanagementsystem.dao.ProjectRepository;
import com.example.charlesb.projectmanagementsystem.dao.UserRepository;
import com.example.charlesb.projectmanagementsystem.entity.Project;
import com.example.charlesb.projectmanagementsystem.entity.User;
import com.example.charlesb.projectmanagementsystem.enums.Status;
import com.example.charlesb.projectmanagementsystem.helper.ConversionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> findAllAssignedToUser(long userId) {
        Optional<User> foundUser = userRepository.findById(userId);

        if (foundUser.isEmpty()) {
            return null;
        }

        return projectRepository.findAllByAssignedTo(foundUser.get());
    }

    @Override
    public List<Project> findAllCreatedByUser(long userId) {
        Optional<User> foundUser = userRepository.findById(userId);

        if (foundUser.isEmpty()) {
            return null;
        }

        return projectRepository.findAllByCreatedBy(foundUser.get());
    }

    @Override
    public List<Project> findInProgressByUser(long userId) {
        Optional<User> foundUser = userRepository.findById(userId);

        if (foundUser.isEmpty()) {
            return null;
        }

        List<Project> projects = projectRepository.findAllByAssignedToAndStatus(foundUser.get(), Status.IN_PROGRESS);

        projects.sort(new Comparator<Project>() {
            @Override
            public int compare(Project o1, Project o2) {
                return ConversionHelper.priorityToInt(o1.getPriority()) - ConversionHelper.priorityToInt(o2.getPriority());
            }
        });

        return projects;
    }

    @Override
    public Project findById(long projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }

    @Override
    public void save(Project project) {
        projectRepository.save(project);
    }

    @Override
    public void deleteById(long projectId) {
        projectRepository.deleteById(projectId);
    }

}
