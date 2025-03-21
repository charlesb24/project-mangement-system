package com.example.charlesb.projectmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
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
@Table(name = "users")
public class User {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")}
    )
    private List<Role> roles = new ArrayList<>();

    @Size(min = 2)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Size(min = 2)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email
    @Column(name = "work_email", unique = true)
    private String workEmail;

    @Email
    @Column(name = "personal_email", unique = true)
    private String personalEmail;

    @Column(name = "work_phone", unique = true)
    private String workPhone;

    @Column(name = "mobile_phone", unique = true)
    private String mobilePhone;

    @Column(name = "manager_id")
    private Long managerId;

    @CreationTimestamp
    @Column(name = "date_created")
    private Instant dateCreated;

    @UpdateTimestamp
    @Column(name = "date_updated")
    private Instant dateUpdated;

}
