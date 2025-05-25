package com.example.charlesb.projectmanagementsystem.enums;

import lombok.Getter;

@Getter
public enum Status {

    NOT_STARTED("Not Started"),
    IN_PROGRESS("In Progress"),
    ON_HOLD("On Hold"),
    CANCELLED("Cancelled"),
    COMPLETE("Complete");

    private final String displayValue;

    Status(String displayValue) {
        this.displayValue = displayValue;
    }

}
