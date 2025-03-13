package com.example.charlesb.projectmanagementsystem.helper;

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

}
