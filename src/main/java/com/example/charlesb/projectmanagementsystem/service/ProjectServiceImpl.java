package com.example.charlesb.projectmanagementsystem.service;

import com.example.charlesb.projectmanagementsystem.dao.ProjectRepository;
import com.example.charlesb.projectmanagementsystem.dao.UserRepository;
import com.example.charlesb.projectmanagementsystem.dto.ProjectDTO;
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
    public void removeAll() {
        projectRepository.deleteAll();
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

    @Override
    public Project mapToProject(ProjectDTO projectDTO) {
        if (projectDTO == null) {
            return null;
        }

        Project project = projectRepository.findById(projectDTO.getId()).orElse(new Project());
        Optional<User> foundUser = userRepository.findById(projectDTO.getAssignedToUserId());

        if (project.getAssignedTo() == null || project.getAssignedTo().getId() != projectDTO.getAssignedToUserId()) {
            if (foundUser.isPresent()) {
                project.setAssignedTo(foundUser.get());
            }
        }

        if (projectDTO.getAssignedToUserId() == 0) {
            project.setAssignedTo(null);
        }

        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setPriority(ConversionHelper.intToPriority(projectDTO.getPriority()));
        project.setStatus(ConversionHelper.intToStatus(projectDTO.getStatus()));

        return project;
    }

    @Override
    public ProjectDTO mapToDTO(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();

        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setPriority(ConversionHelper.priorityToInt(project.getPriority()));
        projectDTO.setStatus(ConversionHelper.statusToInt(project.getStatus()));

        if (project.getAssignedTo() != null) {
            projectDTO.setAssignedToUserId(project.getAssignedTo().getId());
        }

        return projectDTO;
    }
}
