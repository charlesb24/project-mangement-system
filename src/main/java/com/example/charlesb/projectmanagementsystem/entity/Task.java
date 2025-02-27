package com.example.charlesb.projectmanagementsystem.entity;

import jakarta.persistence.*;

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

    @Column(name = "status")
    private int status;

    public Task(Long taskId, Long parentTaskId, int level, String name, String description, int priority, int status) {
        this.taskId = taskId;
        this.parentTaskId = parentTaskId;
        this.level = level;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

    public Task(Long parentTaskId, int level, String name, String description, int priority, int status) {
        this.parentTaskId = parentTaskId;
        this.level = level;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

    public Task(int level, String name, String description, int priority, int status) {
        this.level = level;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

    public Task() {
        this.level = -1;
        this.name = "New Task";
        this.description = "No description.";
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
