package com.example.charlesb.projectmanagementsystem.entity;

import com.example.charlesb.projectmanagementsystem.enums.Status;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;

    // TODO: add validation to ensure this field is populated when level >= 2
    @Column(name = "parent_task_id")
    private Long parentTaskId;

    @Column(name = "level", nullable = false)
    private int level;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "priority")
    private int priority;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private Status status;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "assigned_to")
    private String assignedTo;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    public Task(Long taskId, Long parentTaskId, int level, String name, String description, int priority, Status status, String createdBy, String assignedTo) {
        this.taskId = taskId;
        this.parentTaskId = parentTaskId;
        this.level = level;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
    }

    public Task(Long parentTaskId, int level, String name, String description, int priority, Status status, String createdBy, String assignedTo) {
        this.parentTaskId = parentTaskId;
        this.level = level;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
    }

    public Task(int level, String name, String description, int priority, Status status, String createdBy) {
        this.level = level;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.createdBy = createdBy;
    }

    public Task() {
        this.level = -1;
        this.name = "New Task";
        this.description = "No description.";
        this.createdBy = "No User Found";
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(Long parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
