package com.example.charlesb.projectmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

    public long id;
    public String name;
    public String description;
    public int priority;
    public int status;
    public long assignedToUserId;

}
