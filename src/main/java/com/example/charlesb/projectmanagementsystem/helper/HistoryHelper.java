package com.example.charlesb.projectmanagementsystem.helper;

import com.example.charlesb.projectmanagementsystem.objects.Link;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

public class HistoryHelper {

    private static final String baseURL = "http://localhost:8080";

    public static List<Link> getHistoryForRequirement(Long taskId, Long projectId, boolean isNew) {
        List<Link> history = getHistoryForTask(taskId, projectId);

        history.getLast().setCurrent(false);
        Link requirement = new Link(history.getLast().getUrl() + "/requirement/", isNew ? "New Requirement" : "Requirement Details", true);

        history.add(requirement);

        return history;
    }

    public static List<Link> getHistoryForTask(Long taskId, Long projectId) {
        List<Link> history = getHistoryForProject(projectId);

        history.getLast().setCurrent(false);
        Link task = new Link(history.getLast().getUrl() + "/task/" + taskId, "Task Details", true);

        history.add(task);

        return history;
    }

    public static List<Link> getHistoryForProject(Long projectId) {
        List<Link> history = new ArrayList<>();

        Link home = new Link(baseURL + "/home", "Home", false);
        Link project = new Link(baseURL + "/project/" + projectId, "Project Details", true);

        history.add(home);
        history.add(project);

        return history;
    }

}
