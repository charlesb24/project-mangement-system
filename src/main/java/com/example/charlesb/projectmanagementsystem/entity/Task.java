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

}
