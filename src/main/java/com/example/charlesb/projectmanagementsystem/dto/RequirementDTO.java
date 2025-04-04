package com.example.charlesb.projectmanagementsystem.dto;

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
    public long taskId;
    public String title;
    public String description;
    public int status;
    public long assignedToUserId;

}
