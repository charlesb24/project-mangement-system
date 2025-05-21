package com.example.charlesb.projectmanagementsystem.service;

import com.example.charlesb.projectmanagementsystem.dao.ProjectRepository;
import com.example.charlesb.projectmanagementsystem.entity.Project;
import com.example.charlesb.projectmanagementsystem.entity.User;
import com.example.charlesb.projectmanagementsystem.enums.Status;
import com.example.charlesb.projectmanagementsystem.helper.ConversionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> findAllAssignedToUser(User user) {
        return projectRepository.findAllByAssignedTo(user);
    }

    @Override
    public List<Project> findAllCreatedByUser(User user) {
        return projectRepository.findAllByCreatedBy(user);
    }

    @Override
    public List<Project> findAllInProgressByUser(User user) {
        List<Project> projects = projectRepository.findAllByAssignedToAndStatus(user, Status.IN_PROGRESS);

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
