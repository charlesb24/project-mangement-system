package com.example.charlesb.projectmanagementsystem.enums;

import lombok.Getter;

@Getter
public enum Priority {

    LOW("Low", "primary"),
    MEDIUM("Medium", "success"),
    HIGH("High", "warning"),
    URGENT("Urgent", "danger");

    private final String displayValue;
    private final String style;

    Priority(String displayValue, String style) {
        this.displayValue = displayValue;
        this.style = style;
    }

}
