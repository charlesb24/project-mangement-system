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
public class RequirementDTO {

    public long id;

    @NotNull
    public long taskId;

    @NotNull
    public String title;

    @NotNull
    public String description;

    public int status;
    public long assignedToUserId;

}
