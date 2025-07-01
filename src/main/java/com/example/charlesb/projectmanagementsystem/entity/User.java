package com.example.charlesb.projectmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "users")
public class User {

    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @Column(name = "locked", nullable = false)
    private boolean locked = false;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")}
    )
    private Set<Role> roles = new HashSet<>();

    @Size(min = 2)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Size(min = 2)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "contact_method")
    private String contactMethod;

    @Column(name = "manager_id")
    private Long managerId;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY,
            cascade={CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST},
            orphanRemoval = true)
    private List<Project> createdProjects = new ArrayList<>();

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY,
            cascade={CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST},
            orphanRemoval = true)
    private List<Task> createdTasks = new ArrayList<>();

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY,
            cascade={CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST},
            orphanRemoval = true)
    private List<Requirement> createdRequirements = new ArrayList<>();

    @OneToMany(mappedBy = "assignedTo", fetch = FetchType.LAZY,
            cascade={CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST},
            orphanRemoval = true)
    private List<Project> assignedProjects = new ArrayList<>();

    @OneToMany(mappedBy = "assignedTo", fetch = FetchType.LAZY,
            cascade={CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST},
            orphanRemoval = true)
    private List<Task> assignedTasks = new ArrayList<>();

    @OneToMany(mappedBy = "assignedTo", fetch = FetchType.LAZY,
            cascade={CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST},
            orphanRemoval = true)
    private List<Requirement> assignedRequirements = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "date_created")
    private Instant dateCreated;

    @UpdateTimestamp
    @Column(name = "date_updated")
    private Instant dateUpdated;

    public boolean hasRole(String roleName) {
        return this.roles.stream().anyMatch(r -> r.getName().equals(roleName));
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }

}
