INSERT INTO TASKS (ID, TITLE, DESCRIPTION, STATUS, PRIORITY, CREATETIME, UPDATETIME, AUTHORID) VALUES
    (1, 'Task 1', 'Description of Task 1', 'New', 'High', '2024-11-20 10:00:00', '2024-11-20 10:00:00', 1),
    (2, 'Task 2', 'Description of Task 2', 'New', 'Medium', '2024-11-20 10:30:00', '2024-11-20 10:30:00', 1),
    (3, 'Task 3', 'Description of Task 3', 'New', 'Low', '2024-11-20 11:00:00', '2024-11-20 11:00:00', 1);

INSERT INTO TASKS (ID, TITLE, DESCRIPTION, STATUS, PRIORITY, CREATETIME, UPDATETIME, AUTHORID, PERFORMERID) VALUES
    (4, 'Task 4', 'Description of Task 4', 'In_Progress', 'High', '2024-11-20 11:30:00', '2024-11-20 11:35:00', 1, 2),
    (5, 'Task 5', 'Description of Task 5', 'In_Progress', 'Medium', '2024-11-20 12:00:00', '2024-11-20 12:10:00', 1, 3);