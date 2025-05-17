package com.example.charlesb.projectmanagementsystem.helper;

import com.example.charlesb.projectmanagementsystem.enums.Priority;
import com.example.charlesb.projectmanagementsystem.enums.Status;

public class ConversionHelper {

    public static Status intToStatus(int value) {
        return switch (value) {
            case 1 -> Status.IN_PROGRESS;
            case 2 -> Status.ON_HOLD;
            case 3 -> Status.CANCELLED;
            case 4 -> Status.COMPLETE;
            default -> Status.NOT_STARTED;
        };
    }

    public static int statusToInt(Status status) {
        return switch (status) {
            case IN_PROGRESS -> 1;
            case ON_HOLD -> 2;
            case CANCELLED -> 3;
            case COMPLETE -> 4;
            default -> 0;
        };
    }

    public static Priority intToPriority(int value) {
        return switch (value) {
            case 1 -> Priority.MEDIUM;
            case 2 -> Priority.HIGH;
            case 3 -> Priority.URGENT;
            default -> Priority.LOW;
        };
    }

    public static int priorityToInt(Priority priority) {
        return switch (priority) {
            case MEDIUM -> 1;
            case HIGH -> 2;
            case URGENT -> 3;
            default -> 0;
        };
    }

}
