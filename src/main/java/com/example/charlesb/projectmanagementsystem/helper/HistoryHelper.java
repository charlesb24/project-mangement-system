package com.example.charlesb.projectmanagementsystem.helper;

import com.example.charlesb.projectmanagementsystem.objects.Link;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

public class HistoryHelper {

    @Value("${baseURL:'http://localhost:8080'}")
    private static String baseURL;

    public static List<Link> getHistoryForRequirement(Long requirementId, Long taskId, Long projectId) {
        List<Link> history = getHistoryForTask(taskId, projectId);

        history.getLast().setCurrent(false);
        Link requirement = new Link(baseURL + "/requirement/" + requirementId, "Requirement Details", true);

        history.add(requirement);

        return history;
    }

    public static List<Link> getHistoryForTask(Long taskId, Long projectId) {
        List<Link> history = getHistoryForProject(projectId);

        history.getLast().setCurrent(false);
        Link task = new Link(baseURL + "/task/" + taskId, "Task Details", true);

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
