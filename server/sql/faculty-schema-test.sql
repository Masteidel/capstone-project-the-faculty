drop database if exists faculty_test;
create database faculty_test;
use faculty_test;

-- create tables and relationships
create table app_user (
	app_user_id varchar(36) primary key,
    username varchar(36) not null,
    password_hash varchar(2048) not null,
    disabled tinyint(1) not null
);

create table app_role (
	app_role_id varchar(36) primary key,
    `name` varchar(50) not null
);

create table app_user_role (
	app_user_id varchar(36) not null,
    app_role_id varchar(36) not null,
    constraint pk_app_user_role
		primary key(app_user_id, app_role_id),
	constraint fk_app_user_id
        foreign key (app_user_id)
        references app_user(app_user_id),
	constraint fk_app_role_id
        foreign key (app_role_id)
        references app_role(app_role_id)
	
);

create table student (
    student_id varchar(36) primary key,
    first_name varchar(25) not null,
    last_name varchar(250) not null,
    email varchar(250) not null,
    phone varchar(250) not null,
    major varchar(250) not null,
    `year` varchar(25) not null,
    credits int not null,
    app_user_id varchar(36) not null,
    constraint fk_student_app_user
        foreign key (app_user_id)
        references app_user(app_user_id)
);

create table professor (
    professor_id varchar(36) primary key,
    first_name varchar(25) not null,
    last_name varchar(250) not null,
    email varchar(250) not null,
    phone varchar(250) not null,
    app_user_id varchar(36) not null,
    constraint fk_professor_app_user
        foreign key (app_user_id)
        references app_user(app_user_id)
);

create table course (
	course_id varchar(36) primary key,
    `name` varchar(250) not null,
    `subject` varchar(100) not null,
    credits int not null
);

create table section (
	section_id varchar(36) primary key,
    abbreviation varchar(2) not null,
    student_cap int not null,
    course_id varchar(36) not null,
    professor_id varchar(36) not null,
    constraint fk_course_id
        foreign key (course_id)
        references course(course_id),
	constraint fk_professor_id
        foreign key (professor_id)
        references professor(professor_id)
);

create table lecture (
	lecture_id varchar(36) primary key,
    `day` varchar(9) not null,
    start_time time not null,
    end_time time not null,
    section_id varchar(36) not null,
    constraint fk_section_id
        foreign key (section_id)
        references section(section_id)
);

create table enrollment (
    application_id varchar(36) primary key,
    `status` varchar(50) not null,
    student_id varchar(36) not null,
    section_id varchar(36) not null,
    constraint fk_enrollment_student_id
        foreign key (student_id)
        references student(student_id),
    constraint fk_enrollment_section_id
        foreign key (section_id)
        references section(section_id)
);

DELIMITER $$

CREATE PROCEDURE set_known_good_state()
BEGIN
    -- Clean up previous data and reset auto-increment values where applicable
    DELETE FROM enrollment;
    DELETE FROM lecture;
    DELETE FROM section;
    DELETE FROM course;
    DELETE FROM professor;
    DELETE FROM student;

    DELETE FROM app_user_role;
    DELETE FROM app_role;
    DELETE FROM app_user;

    -- Insert known good data into app_user table
    INSERT INTO app_user (app_user_id, username, password_hash, disabled) VALUES
        ('1', 'user1', 'hash1', 0),
        ('2', 'user2', 'hash2', 0),
        ('3', 'user3', 'hash3', 0),
        ('4', 'user4', 'hash4', 0);

    -- Insert known good data into app_role table
    INSERT INTO app_role (app_role_id, name) VALUES
        ('1', 'ROLE_STUDENT'),
        ('2', 'ROLE_PROFESSOR');

    -- Insert known good data into app_user_role table
    INSERT INTO app_user_role (app_user_id, app_role_id) VALUES
        ('1', '1'), -- user1 is a student
        ('2', '1'), -- user2 is a student
        ('3', '2'), -- user3 is a professor
        ('4', '2'); -- user4 is a professor

    -- Insert known good data into student table
    INSERT INTO student (student_id, first_name, last_name, email, phone, major, year, credits, app_user_id) VALUES
        ('1', 'John', 'Doe', 'john.doe@gmail.com', '952-555-1234', 'Computer Science', 'Sophomore', 45, '1'),
        ('2', 'Jane', 'Smith', 'jane.smith@gmail.com', '846-555-5678', 'Mathematics', 'Junior', 75, '2');

    -- Insert known good data into professor table
    INSERT INTO professor (professor_id, first_name, last_name, email, phone, app_user_id) VALUES
        ('1', 'David', 'Davis', 'david.davis@gmail.com', '952-555-8765', '3'),
        ('2', 'Rebecca', 'Brown', 'rebecca.brown@gmail.com', '942-555-4321', '4');

    -- Insert known good data into course table (Using UUIDs for course_id)
    INSERT INTO course (course_id, name, subject, credits) VALUES
        (UUID(), 'Intro to Programming', 'CS', 3),
        (UUID(), 'Calculus I', 'MATH', 4);

    -- Use the UUIDs of inserted courses in the section table
    DECLARE intro_to_programming_id CHAR(36);
    DECLARE calculus_i_id CHAR(36);

    -- Capture the UUIDs inserted into the course table
    SELECT course_id INTO intro_to_programming_id FROM course WHERE name = 'Intro to Programming';
    SELECT course_id INTO calculus_i_id FROM course WHERE name = 'Calculus I';

    -- Insert known good data into section table (Use captured UUIDs as course_id)
    INSERT INTO section (section_id, abbreviation, student_cap, course_id, professor_id) VALUES
        (UUID(), 'A', 30, intro_to_programming_id, '1'), -- Section A of Intro to Programming taught by Prof. Davis
        (UUID(), 'B', 30, calculus_i_id, '2'); -- Section B of Calculus I taught by Prof. Brown

    -- Insert known good data into lecture table
    INSERT INTO lecture (lecture_id, day, start_time, end_time, section_id) VALUES
        (UUID(), 'Monday', '09:00:00', '10:30:00', (SELECT section_id FROM section WHERE abbreviation = 'A')),
        (UUID(), 'Wednesday', '09:00:00', '10:30:00', (SELECT section_id FROM section WHERE abbreviation = 'A')),
        (UUID(), 'Tuesday', '11:00:00', '12:30:00', (SELECT section_id FROM section WHERE abbreviation = 'B')),
        (UUID(), 'Thursday', '11:00:00', '12:30:00', (SELECT section_id FROM section WHERE abbreviation = 'B'));

    -- Insert known good data into enrollment table
    INSERT INTO enrollment (application_id, status, student_id, section_id) VALUES
        (UUID(), 'Enrolled', '1', (SELECT section_id FROM section WHERE abbreviation = 'A')), -- John Doe enrolled in Section A
        (UUID(), 'Enrolled', '2', (SELECT section_id FROM section WHERE abbreviation = 'B')); -- Jane Smith enrolled in Section B

END$$

DELIMITER ;


-- insert data into tables
insert into app_user (app_user_id, username, password_hash, disabled) values
('1', 'user1', 'hash1', 0),
('2', 'user2', 'hash2', 0),
('3', 'user3', 'hash3', 0),
('4', 'user4', 'hash4', 0);

insert into app_role (app_role_id, name) values
('1', 'ROLE_STUDENT'),
('2', 'ROLE_PROFESSOR');

insert into app_user_role (app_user_id, app_role_id) values
('1', '1'),
('2', '1'),
('3', '2'),
('4', '2');

insert into student (student_id, first_name, last_name, email, phone, major, `year`, credits, app_user_id) values
('1', 'John', 'Doe', 'john.doe@gmail.com', '952-555-1234', 'Computer Science', 'Sophomore', 45, '1'),
('2', 'Jane', 'Smith', 'jane.smith@gmail.com', '846-555-5678', 'Mathematics', 'Junior', 75, '2');

insert into professor (professor_id, first_name, last_name, email, phone, app_user_id) values
('1', 'David', 'Davis', 'david.davis@gmail.com', '952-555-8765', '3'),
('2', 'Rebecca', 'Brown', 'rebecca.brown@gmail.com', '942-555-4321', '4');

insert into course (course_id, name, subject, credits) values
('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'Intro to Programming', 'CS', 3),
('5a9f4b5c-9e44-4a56-8f89-234e39f1db31', 'Calculus I', 'MATH', 4);

insert into section (section_id, abbreviation, student_cap, course_id, professor_id) values
('1', 'A', 30, '1', '1'), -- Section A of Intro to Programming taught by Prof. Davis
('2', 'B', 30, '2', '2'); -- Section B of Calculus I taught by Prof. Brown

insert into lecture (lecture_id, day, start_time, end_time, section_id) values
('1', 'Monday', '09:00:00', '10:30:00', '1'),
('2', 'Wednesday', '09:00:00', '10:30:00', '1'),
('3', 'Tuesday', '11:00:00', '12:30:00', '2'),
('4', 'Thursday', '11:00:00', '12:30:00', '2');

insert into enrollment (application_id, status, student_id, section_id) values
('1', 'Enrolled', '1', '1'), -- John Doe enrolled in Intro to Programming, Section A
('2', 'Enrolled', '2', '2'); -- Jane Smith enrolled in Calculus I, Section B
