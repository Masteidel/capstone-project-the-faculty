drop database if exists faculty_test;
create database faculty_test;
use faculty_test;

-- create tables and relationships
create table app_user (
	app_user_id BIGINT auto_increment primary key,
    username varchar(36) not null,
    password_hash varchar(2048) not null,
    disabled tinyint(1) not null
);

create table app_role (
	app_role_id BIGINT auto_increment primary key,
    `name` varchar(50) not null
);

create table app_user_role (
	app_user_id BIGINT not null,
    app_role_id BIGINT not null,
    constraint pk_app_user_role
		primary key(app_user_id, app_role_id),
	constraint fk_app_user_id
        foreign key (app_user_id)
        references app_user(app_user_id),
	constraint fk_app_role_id
        foreign key (app_role_id)
        references app_role(app_role_id)
);

CREATE TABLE student (
    student_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(25) NOT NULL,
    last_name VARCHAR(250) NOT NULL,
    email VARCHAR(250) NOT NULL,
    phone VARCHAR(250) NOT NULL,
    major VARCHAR(250) NOT NULL,
    `year` VARCHAR(25) NOT NULL,
    credits INT NOT NULL
);


create table professor (
    professor_id BIGINT auto_increment primary key,
    first_name varchar(25) not null,
    last_name varchar(250) not null,
    email varchar(250) not null,
    phone varchar(250) not null
);

create table course (
	course_id BIGINT auto_increment primary key,
    `name` varchar(250) not null,
    `subject` varchar(100) not null,
    credits int not null
);

create table section (
	section_id BIGINT auto_increment primary key,
    abbreviation varchar(2) not null,
    student_cap int not null,
    course_id BIGINT not null,
    professor_id BIGINT not null,
    constraint fk_course_id
        foreign key (course_id)
        references course(course_id),
	constraint fk_professor_id
        foreign key (professor_id)
        references professor(professor_id)
);

create table lecture (
	lecture_id BIGINT auto_increment primary key,
    `day` varchar(9) not null,
    start_time time not null,
    end_time time not null,
    section_id BIGINT not null,
    constraint fk_section_id
        foreign key (section_id)
        references section(section_id)
);

create table enrollment (
    enrollment_id BIGINT auto_increment primary key,
    `status` varchar(50) not null,
    student_id BIGINT not null,
    section_id BIGINT not null,
    constraint fk_enrollment_student_id
        foreign key (student_id)
        references student(student_id),
    constraint fk_enrollment_section_id
        foreign key (section_id)
        references section(section_id)
);


-- Insert data into tables
insert into app_user (username, password_hash, disabled) values
('user1', 'hash1', 0),
('user2', 'hash2', 0),
('user3', 'hash3', 0),
('user4', 'hash4', 0);

insert into app_role (`name`) values
('ROLE_STUDENT'),
('ROLE_PROFESSOR');

insert into app_user_role (app_user_id, app_role_id) values

(1, 1),
(2, 1),
(3, 2),
(4, 2);

insert into student (first_name, last_name, email, phone, major, `year`, credits) values
('John', 'Doe', 'john.doe@gmail.com', '952-555-1234', 'Computer Science', 'Sophomore', 45),
('Jane', 'Smith', 'jane.smith@gmail.com', '846-555-5678', 'Mathematics', 'Junior', 75),
('Sam', 'Smith', 'sam.smith@gmail.com', '846-372-5678', 'Biology', 'Junior', 72);

insert into professor (first_name, last_name, email, phone) values
('David', 'Davis', 'david.davis@gmail.com', '952-555-8765'),
('Rebecca', 'Brown', 'rebecca.brown@gmail.com', '942-555-4321');

insert into course (`name`, `subject`, credits) values
('Intro to Programming', 'CS', 3),
('Calculus I', 'MATH', 4);

insert into section (abbreviation, student_cap, course_id, professor_id) values
('A', 30, 1, 1), -- Section A of Intro to Programming taught by Prof. Davis
('B', 30, 2, 2); -- Section B of Calculus I taught by Prof. Brown

insert into lecture (`day`, start_time, end_time, section_id) values
('Monday', '09:00:00', '10:30:00', 1),
('Wednesday', '09:00:00', '10:30:00', 1),
('Tuesday', '11:00:00', '12:30:00', 2),
('Thursday', '11:00:00', '12:30:00', 2);

insert into enrollment (`status`, student_id, section_id) values
('Enrolled', 1, 1), -- John Doe enrolled in Intro to Programming, Section A
('Enrolled', 2, 2); -- Jane Smith enrolled in Calculus I, Section B
