CREATE TABLE tasks (
    task_id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    parent_task_id BIGINT,
    level          INTEGER                                 NOT NULL,
    name           VARCHAR(255)                            NOT NULL,
    description    VARCHAR(255)                            NOT NULL,
    priority       INTEGER,
    status         INTEGER,
    CONSTRAINT pk_tasks PRIMARY KEY (task_id)
);

CREATE TABLE users (
    user_id VARCHAR(64) NOT NULL,
    password CHAR(68) NOT NULL,
    enabled BOOLEAN NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (user_id)
);

CREATE TABLE roles (
    user_id VARCHAR(64) NOT NULL,
    role VARCHAR(64) NOT NULL,
    CONSTRAINT fk_roles_users FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE available_roles (
    role VARCHAR(64) NOT NULL,
    CONSTRAINT pk_available_roles PRIMARY KEY (role)
);

INSERT INTO users VALUES ('test', '{bcrypt}$2a$12$5qimeKjwEIhYZggCRqvTWuZKuelgq29cYWByAr1B124pWcOpryZV6', true);
INSERT INTO roles VALUES ('test', 'ADMIN');
INSERT INTO available_roles VALUES ('ADMIN'), ('EMPLOYEE'), ('MANAGER');