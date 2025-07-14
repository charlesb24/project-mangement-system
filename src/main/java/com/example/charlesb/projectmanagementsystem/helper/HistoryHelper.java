package com.example.charlesb.projectmanagementsystem.helper;

import com.example.charlesb.projectmanagementsystem.enums.LinkType;
import com.example.charlesb.projectmanagementsystem.objects.Link;

import java.util.ArrayList;
import java.util.List;

public class HistoryHelper {

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

        Link project = new Link("/projects/" + projectId, pageTitle, true);

        history.getLast().setCurrent(false);
        history.add(project);

        return history;
    }

    public static List<Link> getHistoryForProjectList() {
        List<Link> history = new ArrayList<>();

        Link home = new Link("/", "Home", false);
        Link dashboard = new Link("/dashboard", "Dashboard", false);
        Link projectList = new Link("/projects", "Projects", true);

        history.add(home);
        history.add(dashboard);
        history.add(projectList);

        return history;
    }

    public static List<Link> getHistoryForAdminUser(Long userId, LinkType linkType) {
        List<Link> history = getHistoryForAdminUserList();

        String pageTitle;

        switch (linkType) {
            case NEW -> pageTitle = "New User";
            case EDIT -> pageTitle = "Edit User";
            case VIEW -> pageTitle = "User Details";
            case null, default -> pageTitle = "User";
        }

        Link user = new Link("/admin/users/" + userId, pageTitle, true);

        history.getLast().setCurrent(false);
        history.add(user);

        return history;
    }

    public static List<Link> getHistoryForAdminUserList() {
        List<Link> history = new ArrayList<>();

        Link home = new Link("/", "Home", false);
        Link dashboard = new Link("/dashboard", "Dashboard", false);
        Link userList = new Link("/admin/users/list", "All Users", true);

        history.add(home);
        history.add(dashboard);
        history.add(userList);

        return history;
    }

    public static List<Link> getHistoryForManagedUser(Long userId) {
        List<Link> history = getHistoryForManagerList();

        Link currentUser = new Link("/manager/user/" + userId, "User Details", true);

        history.getLast().setCurrent(false);
        history.add(currentUser);

        return history;
    }

    public static List<Link> getHistoryForManagerList() {
        List<Link> history = new ArrayList<>();

        Link home = new Link("/", "Home", false);
        Link dashboard = new Link("/dashboard", "Dashboard", false);
        Link userList = new Link("/manager/users/list", "Managed Users", true);

        history.add(home);
        history.add(dashboard);
        history.add(userList);

        return history;
    }

    public static List<Link> getHistoryForUserSelfEdit() {
        List<Link> history = new ArrayList<>();

        Link home = new Link("/", "Home", false);
        Link userDetails = new Link("/user", "User Details", false);
        Link userEdit = new Link("/user/edit", "Edit User", true);

        history.add(home);
        history.add(userDetails);
        history.add(userEdit);

        return history;
    }

}
