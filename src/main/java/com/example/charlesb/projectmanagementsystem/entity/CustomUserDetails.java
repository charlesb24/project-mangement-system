package com.example.charlesb.projectmanagementsystem.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    // default UserDetails fields
    private final String username;
    private String password;
    private boolean enabled;
    private List<GrantedAuthority> authorities;

    // custom fields
    private Long id;

    private String firstName;
    private char middleInitial;
    private String lastName;

    private String workEmail;
    private String personalEmail;

    private String workPhone;
    private String mobilePhone;

    private String managerId;

    private List<Task> createdTasks;
    private List<Task> assignedTasks;

    public CustomUserDetails(String username, String password, boolean enabled, List<GrantedAuthority> authorities, Long id, String firstName, char middleInitial, String lastName, String workEmail, String personalEmail, String workPhone, String mobilePhone, String managerId, List<Task> createdTasks, List<Task> assignedTasks) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
        this.id = id;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.workEmail = workEmail;
        this.personalEmail = personalEmail;
        this.workPhone = workPhone;
        this.mobilePhone = mobilePhone;
        this.managerId = managerId;
        this.createdTasks = createdTasks;
        this.assignedTasks = assignedTasks;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public char getMiddleInitial() {
        return middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getManagerId() {
        return managerId;
    }

    public List<Task> getCreatedTasks() {
        return createdTasks;
    }

    public List<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleInitial(char middleInitial) {
        this.middleInitial = middleInitial;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setCreatedTasks(List<Task> createdTasks) {
        this.createdTasks = createdTasks;
    }

    public void setAssignedTasks(List<Task> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

    public void addCreatedTask(Task task) {
        this.createdTasks.add(task);
    }

    public void addAssignedTask(Task task) {
        this.assignedTasks.add(task);
    }

}
