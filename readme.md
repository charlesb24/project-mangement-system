# Project Management System

Project Management System is a structured, multi-tier to-do list app designed to help individuals and/or
teams plan, execute, and complete work with clarity and control.
Built around a clear three-level hierarchy of projects, tasks, and requirements, Project Management
System lets you break down big projects into manageable goals without losing sight of the bigger picture.

----

## Features

- **Tiered To-Do Structure:** Organize your work into projects, each containing tasks with requirements that are comprised of the smallest level of detail for each project.
- **Self-Hosted:** Project Management System can be deployed on any server or computer, giving you complete control over your data and privacy.
- **Cross-device sync:** Access your to-do list from anywhere, and have your changes track across all your devices.
- **Priority and Status Tracking:** Quickly see the priority and progress of each tier of the structure with color-coded badges.
- **User Assignment:** Assign projects, tasks, and requirements to users, and allow managers to see an overview of the tasks assigned to and created by their managed users.
- **User Management:** Users with the owner or admin role can edit registered user details and roles, as well as disable or delete unnecessary accounts.

----

## Setup

1. Set up a postgres database and modify application.yml to use the correct environment variables (spring.datasource.url, spring.datasource.username, and spring.datasource.password) for your database connection
2. Build the java JAR file by running `.\mvnw clean package`
3. Move the newly created JAR to the desired location and run it using `java -jar path/to/project-management-system-1.0.0.jar`

----

## Demo

To try a demo of this application, you can either run the `heroku-demo` branch of this application locally by following the setup above, or you can test a hosted version at [Heroku](https://project-management-system-d064356f14b3.herokuapp.com).

Sample data and accounts are provided for easy testing in the demo branch, with one user for each role and several different projects, tasks, and requirements to be viewed.

The password for all test accounts is `password` and the various emails to use when logging in are:

- owner@example.com
- admin@example.com
- manager@example.com
- user@example.com

Any changes made to the live demo will be reset every 3 hours.