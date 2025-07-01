package com.example.charlesb.projectmanagementsystem.entity;

import com.example.charlesb.projectmanagementsystem.enums.Priority;
import com.example.charlesb.projectmanagementsystem.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

    private static final long serialVersionUID = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "priority")
    private Priority priority;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY,
            cascade={CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST},
            orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade={CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "created_by_id", nullable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY, cascade={CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "assigned_to_id")
    private User assignedTo;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

}
