CREATE TABLE user_projects
(
    project_id BIGINT NOT NULL,
    user_id    BIGINT NOT NULL,

    PRIMARY KEY (user_id, project_id),

    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);