INSERT INTO users (first_name, last_name, email, is_active, created_at, updated_at)
VALUES
    ('John', 'Doe', 'john.doe@example.com', true, NOW(), NOW()),
    ('Jane', 'Smith', 'jane.smith@example.com', true, NOW(), NOW()),
    ('Alice', 'Brown', 'alice.brown@example.com', false, NOW(), NOW()),
    ('Bob', 'Johnson', 'bob.johnson@example.com', true, NOW(), NOW());

INSERT INTO projects (name, description, status, created_at, updated_at)
VALUES
    ('Project Alpha', 'First sample project', 1, NOW(), NOW()),
    ('Project Beta', 'Second sample project', 1, NOW(), NOW());

INSERT INTO tasks (title, description, status, priority, deadline, user_id, project_id, created_at, updated_at)
VALUES
    ('Task 1', 'First task description', 'IN_PROGRESS', 'HIGH', NOW() + INTERVAL '5 days', 1, 1, NOW(), NOW()),
    ('Task 2', 'Second task description', 'TODO', 'MEDIUM', NOW() + INTERVAL '10 days', 2, 1, NOW(), NOW()),
    ('Task 3', 'Third task description', 'DONE', 'LOW', NULL, 3, 2, NOW(), NOW());

INSERT INTO user_projects (project_id, user_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 3),
    (2, 4);

INSERT INTO comments (context, task_id, user_id, created_at, updated_at)
VALUES
    ('This is the first comment', 1, 1, NOW(), NOW()),
    ('This is the second comment', 2, 2, NOW(), NOW()),
    ('This is another comment', 3, 3, NOW(), NOW());

