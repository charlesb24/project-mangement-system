package com.example.charlesb.projectmanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
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

    @NotNull
    public String name;

    @NotNull
    public String description;

    public int priority;
    public int status;
    public long assignedToUserId;

}
