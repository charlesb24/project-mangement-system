package com.example.charlesb.projectmanagementsystem.dao;

import com.example.charlesb.projectmanagementsystem.entity.Task;
import com.example.charlesb.projectmanagementsystem.entity.User;
import com.example.charlesb.projectmanagementsystem.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByCreatedBy(User user);

    List<Task> findAllByAssignedTo(User user);

    List<Task> findAllByProjectId(Long projectId);

    List<Task> findAllByAssignedToAndStatus(User user, Status status);

}
