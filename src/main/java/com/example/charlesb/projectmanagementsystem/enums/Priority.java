package com.example.charlesb.projectmanagementsystem.enums;

import lombok.Getter;

@Getter
public enum Priority {

    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    URGENT("Urgent");

    private final String displayValue;

    Priority(String displayValue) {
        this.displayValue = displayValue;
    }

}
