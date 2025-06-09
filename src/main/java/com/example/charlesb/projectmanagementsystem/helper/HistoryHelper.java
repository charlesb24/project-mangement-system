package com.example.charlesb.projectmanagementsystem.helper;

import com.example.charlesb.projectmanagementsystem.enums.LinkType;
import com.example.charlesb.projectmanagementsystem.objects.Link;

import java.util.ArrayList;
import java.util.List;

public class HistoryHelper {

    private static final String baseURL = "http://localhost:8080";

    public static List<Link> getHistoryForRequirement(Long taskId, Long projectId, LinkType linkType) {
        List<Link> history = getHistoryForTask(taskId, projectId, LinkType.VIEW);

        String pageTitle;

        switch (linkType) {
            case NEW -> pageTitle = "New Requirement";
            case EDIT -> pageTitle = "Edit Requirement";
            case VIEW -> pageTitle = "Requirement Details";
            case null, default -> pageTitle = "Requirement";
        }

        history.getLast().setCurrent(false);
        Link requirement = new Link(history.getLast().getUrl() + "/requirement/", pageTitle, true);

        history.add(requirement);

        return history;
    }

    public static List<Link> getHistoryForTask(Long taskId, Long projectId, LinkType linkType) {
        List<Link> history = getHistoryForProject(projectId, LinkType.VIEW);

        String pageTitle;

        switch (linkType) {
            case NEW -> pageTitle = "New Task";
            case EDIT -> pageTitle = "Edit Task";
            case VIEW -> pageTitle = "Task Details";
            case null, default -> pageTitle = "Task";
        }

        history.getLast().setCurrent(false);
        Link task = new Link(history.getLast().getUrl() + "/task/" + taskId, pageTitle, true);

        history.add(task);

        return history;
    }

    public static List<Link> getHistoryForProject(Long projectId, LinkType linkType) {
        List<Link> history = getHistoryForProjectList();

        String pageTitle;

        switch (linkType) {
            case NEW -> pageTitle = "New Project";
            case EDIT -> pageTitle = "Edit Project";
            case VIEW -> pageTitle = "Project Details";
            case null, default -> pageTitle = "Project";
        }

        Link project = new Link(baseURL + "/project/" + projectId, pageTitle, true);

        history.getLast().setCurrent(false);
        history.add(project);

        return history;
    }

    public static List<Link> getHistoryForProjectList() {
        List<Link> history = new ArrayList<>();

        Link home = new Link(baseURL + "/home", "Home", false);
        Link projectList = new Link(baseURL + "/projects", "Projects", true);

        history.add(home);
        history.add(projectList);

        return history;
    }

    public static List<Link> getHistoryForAdminUserEdit(Long userId, LinkType linkType) {
        List<Link> history = getHistoryForAdminUserList();

        String pageTitle;

        switch (linkType) {
            case NEW -> pageTitle = "New User";
            case EDIT -> pageTitle = "Edit User";
            case VIEW -> pageTitle = "User Details";
            case null, default -> pageTitle = "User";
        }

        Link user = new Link(baseURL + "/admin/users/" + userId, pageTitle, true);

        history.getLast().setCurrent(false);
        history.add(user);

        return history;
    }

    public static List<Link> getHistoryForAdminUserList() {
        List<Link> history = new ArrayList<>();

        Link home = new Link(baseURL + "/home", "Home", false);
        Link userList = new Link(baseURL + "/admin/users/list", "Users", true);

        history.add(home);
        history.add(userList);

        return history;
    }

}
