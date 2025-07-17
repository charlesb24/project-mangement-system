package com.example.charlesb.projectmanagementsystem.config;

import com.example.charlesb.projectmanagementsystem.dao.RoleRepository;
import com.example.charlesb.projectmanagementsystem.dto.UserDTO;
import com.example.charlesb.projectmanagementsystem.entity.*;
import com.example.charlesb.projectmanagementsystem.enums.Priority;
import com.example.charlesb.projectmanagementsystem.enums.Status;
import com.example.charlesb.projectmanagementsystem.service.ProjectService;
import com.example.charlesb.projectmanagementsystem.service.TaskService;
import com.example.charlesb.projectmanagementsystem.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DataConfig {

    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final RoleRepository roleRepository;

    public DataConfig(UserService userService, ProjectService projectService, TaskService taskService, RoleRepository roleRepository) {
        this.userService = userService;
        this.projectService = projectService;
        this.taskService = taskService;
        this.roleRepository = roleRepository;
    }

    @Bean
    public CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
        return runner -> {
            createDefaultRoles(roleRepository);
            refreshSampleData();
        };
    }

    @Scheduled(cron = "0 0 */3 * * *")
    public void refreshSampleData() {
        taskService.removeAll();
        projectService.removeAll();
        userService.removeAll();

        createSampleData();
    }

    private void createSampleData() {
        UserDTO owner = new UserDTO(
                null,
                "owner@example.com",
                "password",
                null,
                true,
                false,
                "John",
                "Doe",
                "Owner",
                "+1 (000) 867-5309",
                "phone",
                null
        );

        UserDTO admin = new UserDTO(
                null,
                "admin@example.com",
                "password",
                null,
                true,
                false,
                "Jim",
                "Doe",
                "Admin",
                "+1 (000) 222-2222",
                "email",
                null
        );

        UserDTO manager = new UserDTO(
                null,
                "manager@example.com",
                "password",
                null,
                true,
                false,
                "Joe",
                "Doe",
                "Manager",
                "+1 (000) 333-3333",
                "email",
                null
        );

        UserDTO baseUser = new UserDTO(
                null,
                "user@example.com",
                "password",
                null,
                true,
                false,
                "James",
                "Doe",
                "User",
                "+1 (000) 444-4444",
                "phone",
                null
        );

        userService.saveUser(owner);
        userService.saveUser(admin);
        userService.saveUser(manager);
        userService.saveUser(baseUser);

        User savedOwner = userService.findUserByEmail("owner@example.com");
        User savedAdmin = userService.findUserByEmail("admin@example.com");
        User savedManager = userService.findUserByEmail("manager@example.com");
        User savedBase = userService.findUserByEmail("user@example.com");

        savedAdmin.setManagerId(savedOwner.getId());
        savedManager.setManagerId(savedOwner.getId());
        savedBase.setManagerId(savedManager.getId());

        savedAdmin.addRole(roleRepository.findByName("ROLE_ADMIN"));
        savedManager.addRole(roleRepository.findByName("ROLE_MANAGER"));

        userService.updateUser(savedOwner);
        userService.updateUser(savedAdmin);
        userService.updateUser(savedManager);
        userService.updateUser(savedBase);

        Project project1 = new Project();

        project1.setName("Project 1");
        project1.setDescription("Project 1 Description.");
        project1.setPriority(Priority.MEDIUM);
        project1.setStatus(Status.IN_PROGRESS);
        project1.setCreatedBy(savedOwner);
        project1.setAssignedTo(savedAdmin);

        Project project2 = new Project();

        project2.setName("Project 2");
        project2.setDescription("Project 2 Description.");
        project2.setPriority(Priority.HIGH);
        project2.setStatus(Status.COMPLETE);
        project2.setCreatedBy(savedManager);
        project2.setAssignedTo(savedBase);

        Project project3 = new Project();

        project3.setName("Project 3");
        project3.setDescription("Project 3 Description.");
        project3.setPriority(Priority.LOW);
        project3.setStatus(Status.CANCELLED);
        project3.setCreatedBy(savedAdmin);
        project3.setAssignedTo(savedAdmin);

        Project project4 = new Project();

        project4.setName("Project 4");
        project4.setDescription("Project 4 Description.");
        project4.setPriority(Priority.URGENT);
        project4.setStatus(Status.IN_PROGRESS);
        project4.setCreatedBy(savedManager);
        project4.setAssignedTo(savedManager);

        Project project5 = new Project();

        project5.setName("Project 5");
        project5.setDescription("Project 5 Description.");
        project5.setPriority(Priority.MEDIUM);
        project5.setStatus(Status.ON_HOLD);
        project5.setCreatedBy(savedOwner);
        project5.setAssignedTo(savedAdmin);

        Project project6 = new Project();

        project6.setName("Project 6");
        project6.setDescription("Project 6 Description.");
        project6.setPriority(Priority.LOW);
        project6.setStatus(Status.NOT_STARTED);
        project6.setCreatedBy(savedBase);
        project6.setAssignedTo(savedBase);

        projectService.save(project1);
        projectService.save(project2);
        projectService.save(project3);
        projectService.save(project4);
        projectService.save(project5);
        projectService.save(project6);

        Task task1_1 = new Task();

        task1_1.setName("Task 1");
        task1_1.setDescription("Task 1 Description.");
        task1_1.setPriority(Priority.HIGH);
        task1_1.setStatus(Status.NOT_STARTED);
        task1_1.setCreatedBy(savedManager);
        task1_1.setAssignedTo(null);
        task1_1.setProject(project1);

        Task task1_2 = new Task();

        task1_2.setName("Task 2");
        task1_2.setDescription("Task 2 Description.");
        task1_2.setPriority(Priority.MEDIUM);
        task1_2.setStatus(Status.ON_HOLD);
        task1_2.setCreatedBy(savedManager);
        task1_2.setAssignedTo(savedBase);
        task1_2.setProject(project1);

        Task task1_3 = new Task();

        task1_3.setName("Task 3");
        task1_3.setDescription("Task 3 Description.");
        task1_3.setPriority(Priority.URGENT);
        task1_3.setStatus(Status.IN_PROGRESS);
        task1_3.setCreatedBy(savedOwner);
        task1_3.setAssignedTo(savedAdmin);
        task1_3.setProject(project1);

        Task task1_4 = new Task();

        task1_4.setName("Task 4");
        task1_4.setDescription("Task 4 Description.");
        task1_4.setPriority(Priority.MEDIUM);
        task1_4.setStatus(Status.COMPLETE);
        task1_4.setCreatedBy(savedOwner);
        task1_4.setAssignedTo(savedManager);
        task1_4.setProject(project1);

        Task task2_1 = new Task();

        task2_1.setName("Task 5");
        task2_1.setDescription("Task 5 Description.");
        task2_1.setPriority(Priority.URGENT);
        task2_1.setStatus(Status.COMPLETE);
        task2_1.setCreatedBy(savedManager);
        task2_1.setAssignedTo(savedManager);
        task2_1.setProject(project2);

        Task task2_2 = new Task();

        task2_2.setName("Task 6");
        task2_2.setDescription("Task 6 Description.");
        task2_2.setPriority(Priority.LOW);
        task2_2.setStatus(Status.CANCELLED);
        task2_2.setCreatedBy(savedManager);
        task2_2.setAssignedTo(savedBase);
        task2_2.setProject(project2);

        Task task2_3 = new Task();

        task2_3.setName("Task 7");
        task2_3.setDescription("Task 7 Description.");
        task2_3.setPriority(Priority.HIGH);
        task2_3.setStatus(Status.CANCELLED);
        task2_3.setCreatedBy(savedManager);
        task2_3.setAssignedTo(null);
        task2_3.setProject(project2);

        Task task3_1 = new Task();

        task3_1.setName("Task 8");
        task3_1.setDescription("Task 8 Description.");
        task3_1.setPriority(Priority.HIGH);
        task3_1.setStatus(Status.COMPLETE);
        task3_1.setCreatedBy(savedAdmin);
        task3_1.setAssignedTo(savedAdmin);
        task3_1.setProject(project3);

        Task task3_2 = new Task();

        task3_2.setName("Task 9");
        task3_2.setDescription("Task 9 Description.");
        task3_2.setPriority(Priority.MEDIUM);
        task3_2.setStatus(Status.CANCELLED);
        task3_2.setCreatedBy(savedAdmin);
        task3_2.setAssignedTo(null);
        task3_2.setProject(project3);

        Task task4_1 = new Task();

        task4_1.setName("Task 10");
        task4_1.setDescription("Task 10 Description.");
        task4_1.setPriority(Priority.LOW);
        task4_1.setStatus(Status.COMPLETE);
        task4_1.setCreatedBy(savedManager);
        task4_1.setAssignedTo(savedManager);
        task4_1.setProject(project4);

        Task task4_2 = new Task();

        task4_2.setName("Task 11");
        task4_2.setDescription("Task 11 Description.");
        task4_2.setPriority(Priority.MEDIUM);
        task4_2.setStatus(Status.IN_PROGRESS);
        task4_2.setCreatedBy(savedManager);
        task4_2.setAssignedTo(savedBase);
        task4_2.setProject(project4);

        Task task4_3 = new Task();

        task4_3.setName("Task 12");
        task4_3.setDescription("Task 12 Description.");
        task4_3.setPriority(Priority.URGENT);
        task4_3.setStatus(Status.IN_PROGRESS);
        task4_3.setCreatedBy(savedManager);
        task4_3.setAssignedTo(savedManager);
        task4_3.setProject(project4);

        Task task5_1 = new Task();

        task5_1.setName("Task 13");
        task5_1.setDescription("Task 13 Description.");
        task5_1.setPriority(Priority.MEDIUM);
        task5_1.setStatus(Status.COMPLETE);
        task5_1.setCreatedBy(savedOwner);
        task5_1.setAssignedTo(savedOwner);
        task5_1.setProject(project5);

        Task task5_2 = new Task();

        task5_2.setName("Task 14");
        task5_2.setDescription("Task 14 Description.");
        task5_2.setPriority(Priority.HIGH);
        task5_2.setStatus(Status.ON_HOLD);
        task5_2.setCreatedBy(savedOwner);
        task5_2.setAssignedTo(savedAdmin);
        task5_2.setProject(project5);

        Task task5_3 = new Task();

        task5_3.setName("Task 15");
        task5_3.setDescription("Task 15 Description.");
        task5_3.setPriority(Priority.LOW);
        task5_3.setStatus(Status.NOT_STARTED);
        task5_3.setCreatedBy(savedAdmin);
        task5_3.setAssignedTo(savedAdmin);
        task5_3.setProject(project5);

        Task task6_1 = new Task();

        task6_1.setName("Task 16");
        task6_1.setDescription("Task 16 Description.");
        task6_1.setPriority(Priority.LOW);
        task6_1.setStatus(Status.NOT_STARTED);
        task6_1.setCreatedBy(savedBase);
        task6_1.setAssignedTo(null);
        task6_1.setProject(project6);

        Task task6_2 = new Task();

        task6_2.setName("Task 17");
        task6_2.setDescription("Task 17 Description.");
        task6_2.setPriority(Priority.HIGH);
        task6_2.setStatus(Status.NOT_STARTED);
        task6_2.setCreatedBy(savedBase);
        task6_2.setAssignedTo(savedBase);
        task6_2.setProject(project6);

        Task task6_3 = new Task();

        task6_3.setName("Task 18");
        task6_3.setDescription("Task 18 Description.");
        task6_3.setPriority(Priority.URGENT);
        task6_3.setStatus(Status.NOT_STARTED);
        task6_3.setCreatedBy(savedBase);
        task6_3.setAssignedTo(savedBase);
        task6_3.setProject(project6);

        Task task6_4 = new Task();

        task6_4.setName("Task 19");
        task6_4.setDescription("Task 19 Description.");
        task6_4.setPriority(Priority.MEDIUM);
        task6_4.setStatus(Status.NOT_STARTED);
        task6_4.setCreatedBy(savedBase);
        task6_4.setAssignedTo(null);
        task6_4.setProject(project6);

        taskService.save(task1_1);
        taskService.save(task1_2);
        taskService.save(task1_3);
        taskService.save(task1_4);
        taskService.save(task2_1);
        taskService.save(task2_2);
        taskService.save(task2_3);
        taskService.save(task3_1);
        taskService.save(task3_2);
        taskService.save(task4_1);
        taskService.save(task4_2);
        taskService.save(task4_3);
        taskService.save(task5_1);
        taskService.save(task5_2);
        taskService.save(task5_3);
        taskService.save(task6_1);
        taskService.save(task6_2);
        taskService.save(task6_3);
        taskService.save(task6_4);

        Requirement requirement1_1_1 = new Requirement();

        requirement1_1_1.setTitle("Requirement 1");
        requirement1_1_1.setDescription("Requirement 1 Description.");
        requirement1_1_1.setStatus(Status.NOT_STARTED);
        requirement1_1_1.setCreatedBy(savedManager);
        requirement1_1_1.setAssignedTo(null);
        requirement1_1_1.setTask(task1_1);

        Requirement requirement1_2_1 = new Requirement();

        requirement1_2_1.setTitle("Requirement 2");
        requirement1_2_1.setDescription("Requirement 2 Description.");
        requirement1_2_1.setStatus(Status.ON_HOLD);
        requirement1_2_1.setCreatedBy(savedManager);
        requirement1_2_1.setAssignedTo(savedBase);
        requirement1_2_1.setTask(task1_2);

        Requirement requirement1_3_1 = new Requirement();

        requirement1_3_1.setTitle("Requirement 3");
        requirement1_3_1.setDescription("Requirement 3 Description.");
        requirement1_3_1.setStatus(Status.IN_PROGRESS);
        requirement1_3_1.setCreatedBy(savedAdmin);
        requirement1_3_1.setAssignedTo(savedAdmin);
        requirement1_3_1.setTask(task1_3);

        Requirement requirement1_3_2 = new Requirement();

        requirement1_3_2.setTitle("Requirement 4");
        requirement1_3_2.setDescription("Requirement 4 Description.");
        requirement1_3_2.setStatus(Status.IN_PROGRESS);
        requirement1_3_2.setCreatedBy(savedOwner);
        requirement1_3_2.setAssignedTo(savedManager);
        requirement1_3_2.setTask(task1_3);

        Requirement requirement1_4_1 = new Requirement();

        requirement1_4_1.setTitle("Requirement 5");
        requirement1_4_1.setDescription("Requirement 5 Description.");
        requirement1_4_1.setStatus(Status.COMPLETE);
        requirement1_4_1.setCreatedBy(savedOwner);
        requirement1_4_1.setAssignedTo(savedManager);
        requirement1_4_1.setTask(task1_4);

        Requirement requirement2_1_1 = new Requirement();

        requirement2_1_1.setTitle("Requirement 6");
        requirement2_1_1.setDescription("Requirement 6 Description.");
        requirement2_1_1.setStatus(Status.COMPLETE);
        requirement2_1_1.setCreatedBy(savedManager);
        requirement2_1_1.setAssignedTo(savedManager);
        requirement2_1_1.setTask(task2_1);

        Requirement requirement2_1_2 = new Requirement();

        requirement2_1_2.setTitle("Requirement 7");
        requirement2_1_2.setDescription("Requirement 7 Description.");
        requirement2_1_2.setStatus(Status.COMPLETE);
        requirement2_1_2.setCreatedBy(savedManager);
        requirement2_1_2.setAssignedTo(savedBase);
        requirement2_1_2.setTask(task2_1);

        Requirement requirement3_1_1 = new Requirement();

        requirement3_1_1.setTitle("Requirement 8");
        requirement3_1_1.setDescription("Requirement 8 Description.");
        requirement3_1_1.setStatus(Status.COMPLETE);
        requirement3_1_1.setCreatedBy(savedManager);
        requirement3_1_1.setAssignedTo(savedBase);
        requirement3_1_1.setTask(task3_1);

        Requirement requirement3_1_2 = new Requirement();

        requirement3_1_2.setTitle("Requirement 9");
        requirement3_1_2.setDescription("Requirement 9 Description.");
        requirement3_1_2.setStatus(Status.COMPLETE);
        requirement3_1_2.setCreatedBy(savedAdmin);
        requirement3_1_2.setAssignedTo(savedAdmin);
        requirement3_1_2.setTask(task3_1);

        Requirement requirement3_1_3 = new Requirement();

        requirement3_1_3.setTitle("Requirement 10");
        requirement3_1_3.setDescription("Requirement 10 Description.");
        requirement3_1_3.setStatus(Status.CANCELLED);
        requirement3_1_3.setCreatedBy(savedAdmin);
        requirement3_1_3.setAssignedTo(null);
        requirement3_1_3.setTask(task3_1);

        Requirement requirement4_1_1 = new Requirement();

        requirement4_1_1.setTitle("Requirement 11");
        requirement4_1_1.setDescription("Requirement 11 Description.");
        requirement4_1_1.setStatus(Status.COMPLETE);
        requirement4_1_1.setCreatedBy(savedManager);
        requirement4_1_1.setAssignedTo(savedManager);
        requirement4_1_1.setTask(task4_1);

        Requirement requirement4_2_1 = new Requirement();

        requirement4_2_1.setTitle("Requirement 12");
        requirement4_2_1.setDescription("Requirement 12 Description.");
        requirement4_2_1.setStatus(Status.IN_PROGRESS);
        requirement4_2_1.setCreatedBy(savedManager);
        requirement4_2_1.setAssignedTo(savedBase);
        requirement4_2_1.setTask(task4_2);

        Requirement requirement4_3_1 = new Requirement();

        requirement4_3_1.setTitle("Requirement 13");
        requirement4_3_1.setDescription("Requirement 13 Description.");
        requirement4_3_1.setStatus(Status.IN_PROGRESS);
        requirement4_3_1.setCreatedBy(savedManager);
        requirement4_3_1.setAssignedTo(savedManager);
        requirement4_3_1.setTask(task4_3);

        Requirement requirement4_3_2 = new Requirement();

        requirement4_3_2.setTitle("Requirement 14");
        requirement4_3_2.setDescription("Requirement 14 Description.");
        requirement4_3_2.setStatus(Status.IN_PROGRESS);
        requirement4_3_2.setCreatedBy(savedManager);
        requirement4_3_2.setAssignedTo(savedBase);
        requirement4_3_2.setTask(task4_3);

        Requirement requirement5_1_1 = new Requirement();

        requirement5_1_1.setTitle("Requirement 15");
        requirement5_1_1.setDescription("Requirement 15 Description.");
        requirement5_1_1.setStatus(Status.COMPLETE);
        requirement5_1_1.setCreatedBy(savedOwner);
        requirement5_1_1.setAssignedTo(savedOwner);
        requirement5_1_1.setTask(task5_1);

        Requirement requirement5_2_1 = new Requirement();

        requirement5_2_1.setTitle("Requirement 16");
        requirement5_2_1.setDescription("Requirement 16 Description.");
        requirement5_2_1.setStatus(Status.ON_HOLD);
        requirement5_2_1.setCreatedBy(savedOwner);
        requirement5_2_1.setAssignedTo(savedAdmin);
        requirement5_2_1.setTask(task5_2);

        Requirement requirement5_2_2 = new Requirement();

        requirement5_2_2.setTitle("Requirement 17");
        requirement5_2_2.setDescription("Requirement 17 Description.");
        requirement5_2_2.setStatus(Status.NOT_STARTED);
        requirement5_2_2.setCreatedBy(savedAdmin);
        requirement5_2_2.setAssignedTo(null);
        requirement5_2_2.setTask(task5_2);

        Requirement requirement6_1_1 = new Requirement();

        requirement6_1_1.setTitle("Requirement 18");
        requirement6_1_1.setDescription("Requirement 18 Description.");
        requirement6_1_1.setStatus(Status.NOT_STARTED);
        requirement6_1_1.setCreatedBy(savedBase);
        requirement6_1_1.setAssignedTo(savedBase);
        requirement6_1_1.setTask(task6_1);

        Requirement requirement6_1_2 = new Requirement();

        requirement6_1_2.setTitle("Requirement 19");
        requirement6_1_2.setDescription("Requirement 19 Description.");
        requirement6_1_2.setStatus(Status.NOT_STARTED);
        requirement6_1_2.setCreatedBy(savedBase);
        requirement6_1_2.setAssignedTo(null);
        requirement6_1_2.setTask(task6_1);

        Requirement requirement6_2_1 = new Requirement();

        requirement6_2_1.setTitle("Requirement 20");
        requirement6_2_1.setDescription("Requirement 20 Description.");
        requirement6_2_1.setStatus(Status.NOT_STARTED);
        requirement6_2_1.setCreatedBy(savedBase);
        requirement6_2_1.setAssignedTo(savedBase);
        requirement6_2_1.setTask(task6_2);

        taskService.saveRequirement(requirement1_1_1);
        taskService.saveRequirement(requirement1_2_1);
        taskService.saveRequirement(requirement1_3_1);
        taskService.saveRequirement(requirement1_3_2);
        taskService.saveRequirement(requirement1_4_1);
        taskService.saveRequirement(requirement2_1_1);
        taskService.saveRequirement(requirement2_1_2);
        taskService.saveRequirement(requirement3_1_1);
        taskService.saveRequirement(requirement3_1_2);
        taskService.saveRequirement(requirement3_1_3);
        taskService.saveRequirement(requirement4_1_1);
        taskService.saveRequirement(requirement4_2_1);
        taskService.saveRequirement(requirement4_3_1);
        taskService.saveRequirement(requirement4_3_2);
        taskService.saveRequirement(requirement5_1_1);
        taskService.saveRequirement(requirement5_2_1);
        taskService.saveRequirement(requirement5_2_2);
        taskService.saveRequirement(requirement6_1_1);
        taskService.saveRequirement(requirement6_1_2);
        taskService.saveRequirement(requirement6_2_1);
    }

    private void createDefaultRoles(RoleRepository repo) {
        if (repo.count() == 0) {
            Role user = new Role();
            Role manager = new Role();
            Role admin = new Role();
            Role owner = new Role();

            user.setName("ROLE_USER");
            manager.setName("ROLE_MANAGER");
            admin.setName("ROLE_ADMIN");
            owner.setName("ROLE_OWNER");

            repo.save(user);
            repo.save(manager);
            repo.save(admin);
            repo.save(owner);
        }
    }

}
