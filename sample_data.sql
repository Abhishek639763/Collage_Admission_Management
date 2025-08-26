-- Sample data to try the system quickly
INSERT INTO students (name, email, marks) VALUES
('Alice Kumar','alice@example.com',92.5),
('Bob Singh','bob@example.com',85.0),
('Chetan Sharma','chetan@example.com',78.0),
('Diana Roy','diana@example.com',88.2),
('Evan Mehta','evan@example.com',69.5);

INSERT INTO courses (code, name, seats, cutoff) VALUES
('CS101','BSc Computer Science',2,80.0),
('ENG01','B.A. English',2,70.0),
('BUS01','BBA',2,75.0);

-- Applications (student_id, course_id, merit_score)
INSERT INTO applications (student_id, course_id, merit_score) VALUES
(1,1,92.5),
(2,1,85.0),
(3,1,78.0),
(4,2,88.2),
(5,2,69.5),
(2,3,85.0),
(4,3,88.2);
