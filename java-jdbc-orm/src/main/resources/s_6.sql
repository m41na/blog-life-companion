drop table if exists tbl_task;
drop table if exists tbl_user;

create table if not exists tbl_user (
	id UUID default random_uuid(),
	email_address varchar(100) not null,
	nickname varchar(50),
	date_joined timestamp not null default now(),
	access_level varchar default 'VISITOR',
	addr_city varchar(50),
 	addr_state varchar(30),
 	addr_postal_code varchar(10),
	constraint customer_pk primary key(email_address, nickname)
);

create table if not exists tbl_task (
	id UUID default random_uuid(),
	name varchar(50) not null,
	done boolean not null default false,
	task_created timestamp not null default now(),
	parent_task UUID,
	task_assignee UUID,
	constraint task_pk primary key(id),
	constraint uniq_name unique (name),
	constraint task_parent_fk foreign key(parent_task) references tbl_task(id),
	constraint task_assignee_fk foreign key(task_assignee) references tbl_user(id)
);

INSERT INTO TBL_USER (email_address, NICKNAME) VALUES ('user1@email.com', 'user one');
INSERT INTO TBL_USER (email_address, NICKNAME) VALUES ('user2@email.com', 'user two');
INSERT INTO TBL_USER (email_address, NICKNAME) VALUES ('user3@email.com', 'user three');
INSERT INTO TBL_USER (email_address, NICKNAME) VALUES ('user4@email.com', 'user four');
INSERT INTO TBL_USER (email_address, NICKNAME) VALUES ('user5@email.com', 'user five');

INSERT INTO TBL_TASK (name) VALUES ('task 1');
INSERT INTO TBL_TASK (name) VALUES ('task 2');
INSERT INTO TBL_TASK (name) VALUES ('task 3');
INSERT INTO TBL_TASK (name) VALUES ('task 4');
INSERT INTO TBL_TASK (name) VALUES ('task 5');
INSERT INTO TBL_TASK (name) VALUES ('task 6');
INSERT INTO TBL_TASK (name) VALUES ('task 7');
INSERT INTO TBL_TASK (name) VALUES ('task 8');
INSERT INTO TBL_TASK (name) VALUES ('task 9');
INSERT INTO TBL_TASK (name) VALUES ('task 10');
INSERT INTO TBL_TASK (name) VALUES ('task 11');

UPDATE TBL_TASK SET PARENT_TASK = (SELECT id FROM TBL_TASK WHERE name = 'task 1') WHERE name = 'task 2';
UPDATE TBL_TASK SET PARENT_TASK = (SELECT id FROM TBL_TASK WHERE name = 'task 2') WHERE name = 'task 3';
UPDATE TBL_TASK SET PARENT_TASK = (SELECT id FROM TBL_TASK WHERE name = 'task 3') WHERE name = 'task 4';
UPDATE TBL_TASK SET PARENT_TASK = (SELECT id FROM TBL_TASK WHERE name = 'task 4') WHERE name = 'task 5';
UPDATE TBL_TASK SET PARENT_TASK = (SELECT id FROM TBL_TASK WHERE name = 'task 5') WHERE name = 'task 6';
UPDATE TBL_TASK SET PARENT_TASK = (SELECT id FROM TBL_TASK WHERE name = 'task 6') WHERE name = 'task 7';
UPDATE TBL_TASK SET PARENT_TASK = (SELECT id FROM TBL_TASK WHERE name = 'task 7') WHERE name = 'task 8';
UPDATE TBL_TASK SET PARENT_TASK = (SELECT id FROM TBL_TASK WHERE name = 'task 8') WHERE name = 'task 9';
UPDATE TBL_TASK SET PARENT_TASK = (SELECT id FROM TBL_TASK WHERE name = 'task 9') WHERE name = 'task 10';
UPDATE TBL_TASK SET PARENT_TASK = (SELECT id FROM TBL_TASK WHERE name = 'task 10') WHERE name = 'task 11';

UPDATE TBL_TASK SET TASK_ASSIGNEE = (SELECT id FROM TBL_USER WHERE email_address = 'user1@email.com') WHERE NAME = 'task 2';
UPDATE TBL_TASK SET TASK_ASSIGNEE = (SELECT id FROM TBL_USER WHERE email_address = 'user1@email.com') WHERE NAME = 'task 3';
UPDATE TBL_TASK SET TASK_ASSIGNEE = (SELECT id FROM TBL_USER WHERE email_address = 'user2@email.com') WHERE NAME = 'task 4';
UPDATE TBL_TASK SET TASK_ASSIGNEE = (SELECT id FROM TBL_USER WHERE email_address = 'user2@email.com') WHERE NAME = 'task 5';
UPDATE TBL_TASK SET TASK_ASSIGNEE = (SELECT id FROM TBL_USER WHERE email_address = 'user3@email.com') WHERE NAME = 'task 6';
UPDATE TBL_TASK SET TASK_ASSIGNEE = (SELECT id FROM TBL_USER WHERE email_address = 'user3@email.com') WHERE NAME = 'task 7';
UPDATE TBL_TASK SET TASK_ASSIGNEE = (SELECT id FROM TBL_USER WHERE email_address = 'user4@email.com') WHERE NAME = 'task 8';
UPDATE TBL_TASK SET TASK_ASSIGNEE = (SELECT id FROM TBL_USER WHERE email_address = 'user4@email.com') WHERE NAME = 'task 9';
UPDATE TBL_TASK SET TASK_ASSIGNEE = (SELECT id FROM TBL_USER WHERE email_address = 'user1@email.com') WHERE NAME = 'task 10';
UPDATE TBL_TASK SET TASK_ASSIGNEE = (SELECT id FROM TBL_USER WHERE email_address = 'user2@email.com') WHERE NAME = 'task 11';
UPDATE TBL_TASK SET TASK_ASSIGNEE = (SELECT id FROM TBL_USER WHERE email_address = 'user3@email.com') WHERE NAME = 'task 1';

drop table if exists tbl_task_v2;
drop table if exists tbl_user_v2;

create table if not exists tbl_user_v2 (
  email_address varchar(100) not null,
  username varchar(50) not null,
  nickname varchar(50),
  date_joined timestamp not null default now(),
  access_level varchar default 'VISITOR',
  addr_city varchar(50),
  addr_state varchar(30),
  addr_postal_code varchar(10),
  constraint customer_pk_v2 primary key(email_address, username)
);

create table if not exists tbl_task_v2 (
  num int NOT null,
  name varchar(50) not null,
  done boolean not null default false,
  task_created timestamp not null default now(),
  parent_task_num int,
  parent_task_name varchar(50),
  task_assignee_email varchar(100),
  task_assignee_uname varchar(50),
  constraint task_pk_v2 primary key(num, name),
  constraint task_parent_fk_v2 foreign key(parent_task_num, parent_task_name) references tbl_task_v2(num, name),
  CONSTRAINT task_assignee_fk_v2 FOREIGN key(task_assignee_email, task_assignee_uname) REFERENCES tbl_user_v2(email_address, username)
);

INSERT INTO TBL_USER_v2 (email_address, username, NICKNAME) VALUES ('user1@email.com', 'one', 'user one');
INSERT INTO TBL_USER_v2 (email_address, username, NICKNAME) VALUES ('user2@email.com', 'two', 'user two');
INSERT INTO TBL_USER_v2 (email_address, username, NICKNAME) VALUES ('user3@email.com', 'three', 'user three');
INSERT INTO TBL_USER_v2 (email_address, username, NICKNAME) VALUES ('user4@email.com', 'four', 'user four');
INSERT INTO TBL_USER_v2 (email_address, username, NICKNAME) VALUES ('user5@email.com', 'five', 'user five');

INSERT INTO TBL_TASK_V2 (name, num) VALUES ('task 1', 1);
INSERT INTO TBL_TASK_V2 (name, num) VALUES ('task 2', 2);
INSERT INTO TBL_TASK_V2 (name, num) VALUES ('task 3', 3);
INSERT INTO TBL_TASK_V2 (name, num) VALUES ('task 4', 4);
INSERT INTO TBL_TASK_V2 (name, num) VALUES ('task 5', 5);
INSERT INTO TBL_TASK_V2 (name, num) VALUES ('task 6', 6);
INSERT INTO TBL_TASK_V2 (name, num) VALUES ('task 7', 7);
INSERT INTO TBL_TASK_V2 (name, num) VALUES ('task 8', 8);
INSERT INTO TBL_TASK_V2 (name, num) VALUES ('task 9', 9);
INSERT INTO TBL_TASK_V2 (name, num) VALUES ('task 10', 10);
INSERT INTO TBL_TASK_V2 (name, num) VALUES ('task 11', 10);

UPDATE TBL_TASK_V2 SET PARENT_TASK_NAME = 'task 1', PARENT_TASK_NUM = 1 WHERE name = 'task 2' AND num = 2;
UPDATE TBL_TASK_V2 SET PARENT_TASK_NAME = 'task 2', PARENT_TASK_NUM = 2  WHERE name = 'task 3' AND num = 3;
UPDATE TBL_TASK_V2 SET PARENT_TASK_NAME = 'task 3', PARENT_TASK_NUM = 3  WHERE name = 'task 4' AND num = 4;
UPDATE TBL_TASK_V2 SET PARENT_TASK_NAME = 'task 4', PARENT_TASK_NUM = 4  WHERE name = 'task 5' AND num = 5;
UPDATE TBL_TASK_V2 SET PARENT_TASK_NAME = 'task 5', PARENT_TASK_NUM = 5  WHERE name = 'task 6' AND num = 6;
UPDATE TBL_TASK_V2 SET PARENT_TASK_NAME = 'task 6', PARENT_TASK_NUM = 6  WHERE name = 'task 7' AND num = 7;
UPDATE TBL_TASK_V2 SET PARENT_TASK_NAME = 'task 7', PARENT_TASK_NUM = 7  WHERE name = 'task 8' AND num = 8;
UPDATE TBL_TASK_V2 SET PARENT_TASK_NAME = 'task 8', PARENT_TASK_NUM = 8  WHERE name = 'task 9' AND num = 9;
UPDATE TBL_TASK_V2 SET PARENT_TASK_NAME = 'task 9', PARENT_TASK_NUM = 9  WHERE name = 'task 10' AND num = 10;
UPDATE TBL_TASK_V2 SET PARENT_TASK_NAME = 'task 10', PARENT_TASK_NUM = 10  WHERE name = 'task 11' AND num = 11;

UPDATE TBL_TASK_V2 SET TASK_ASSIGNEE_EMAIL = 'user1@email.com', TASK_ASSIGNEE_UNAME = 'one' WHERE NAME = 'task 2' AND NUM = 2;
UPDATE TBL_TASK_V2 SET TASK_ASSIGNEE_EMAIL = 'user1@email.com', TASK_ASSIGNEE_UNAME = 'one' WHERE NAME = 'task 3' AND NUM = 3;
UPDATE TBL_TASK_V2 SET TASK_ASSIGNEE_EMAIL = 'user2@email.com', TASK_ASSIGNEE_UNAME = 'two' WHERE NAME = 'task 4' AND NUM = 4;
UPDATE TBL_TASK_V2 SET TASK_ASSIGNEE_EMAIL = 'user2@email.com', TASK_ASSIGNEE_UNAME = 'two' WHERE NAME = 'task 5' AND NUM = 5;
UPDATE TBL_TASK_V2 SET TASK_ASSIGNEE_EMAIL = 'user3@email.com', TASK_ASSIGNEE_UNAME = 'three' WHERE NAME = 'task 6' AND NUM = 6;
UPDATE TBL_TASK_V2 SET TASK_ASSIGNEE_EMAIL = 'user3@email.com', TASK_ASSIGNEE_UNAME = 'three' WHERE NAME = 'task 7' AND NUM = 7;
UPDATE TBL_TASK_V2 SET TASK_ASSIGNEE_EMAIL = 'user4@email.com', TASK_ASSIGNEE_UNAME = 'four' WHERE NAME = 'task 8' AND NUM = 8;
UPDATE TBL_TASK_V2 SET TASK_ASSIGNEE_EMAIL = 'user4@email.com', TASK_ASSIGNEE_UNAME = 'four' WHERE NAME = 'task 9' AND NUM = 9;
UPDATE TBL_TASK_V2 SET TASK_ASSIGNEE_EMAIL = 'user1@email.com', TASK_ASSIGNEE_UNAME = 'one' WHERE NAME = 'task 10' AND NUM = 10;
UPDATE TBL_TASK_V2 SET TASK_ASSIGNEE_EMAIL = 'user2@email.com', TASK_ASSIGNEE_UNAME = 'two' WHERE NAME = 'task 11' AND NUM = 11;
UPDATE TBL_TASK_V2 SET TASK_ASSIGNEE_EMAIL = 'user3@email.com', TASK_ASSIGNEE_UNAME = 'three' WHERE NAME = 'task 1' AND NUM = 1;

SELECT * FROM TBL_TASK tt;

SELECT * FROM TBL_USER tu;

SELECT * FROM TBL_TASK_V2 tt2;

SELECT * FROM TBL_USER_V2 tu2;

select PK_TABLE.* from tbl_task_v2 PK_TABLE
inner join tbl_user_v2 FK_TABLE on PK_TABLE.task_assignee_email = FK_TABLE.email_address
and PK_TABLE.task_assignee_uname = FK_TABLE.username
where PK_TABLE.task_assignee_email = 'user1@email.com' and PK_TABLE.task_assignee_uname = 'one';

select PK_TABLE.* from tbl_task_v2 PK_TABLE
inner join tbl_user_v2 FK_TABLE on PK_TABLE.task_assignee_email = FK_TABLE.email_address
and PK_TABLE.task_assignee_uname = FK_TABLE.username
where PK_TABLE.task_assignee_email = 'user3@email.com' and PK_TABLE.task_assignee_uname = 'three';


DROP TABLE IF EXISTS tbl_user_task;
DROP TABLE IF EXISTS tbl_task_v3;
DROP TABLE IF EXISTS tbl_user_v3;

create table if not exists tbl_user_v3 (
	id UUID default random_uuid(),
	email_address varchar(100) not null,
	nickname varchar(50),
	date_joined timestamp not null default now(),
	access_level varchar default 'VISITOR',
	addr_city varchar(50),
 	addr_state varchar(30),
 	addr_postal_code varchar(10),
	constraint user_pk_v3 primary key(id)
);

create table if not exists tbl_task_v3 (
	id UUID default random_uuid(),
	name varchar(50) not null,
	done boolean not null default false,
	task_created timestamp not null default now(),
	parent_task UUID,
	constraint task_pk_v3 primary key(id),
	constraint uniq_name_v3 unique (name),
	constraint task_parent_fk_v3 foreign key(parent_task) references tbl_task(id)
);

create table if not exists tbl_user_task (
    task_id UUID not null,
    task_assignee UUID not null,
    constraint user_task_id_fk foreign key(task_id) references tbl_task_v3(id),
    constraint user_task_assignee_fk foreign key(task_assignee) references tbl_user_v3(id)
);

INSERT INTO TBL_USER_V3 (email_address, NICKNAME) VALUES ('user1@email.com', 'user one');
INSERT INTO TBL_USER_V3 (email_address, NICKNAME) VALUES ('user2@email.com', 'user two');
INSERT INTO TBL_USER_V3 (email_address, NICKNAME) VALUES ('user3@email.com', 'user three');
INSERT INTO TBL_USER_V3 (email_address, NICKNAME) VALUES ('user4@email.com', 'user four');
INSERT INTO TBL_USER_V3 (email_address, NICKNAME) VALUES ('user5@email.com', 'user five');

INSERT INTO TBL_TASK_V3 (name) VALUES ('task 1');
INSERT INTO TBL_TASK_V3 (name) VALUES ('task 2');
INSERT INTO TBL_TASK_V3 (name) VALUES ('task 3');
INSERT INTO TBL_TASK_V3 (name) VALUES ('task 4');
INSERT INTO TBL_TASK_V3 (name) VALUES ('task 5');
INSERT INTO TBL_TASK_V3 (name) VALUES ('task 6');
INSERT INTO TBL_TASK_V3 (name) VALUES ('task 7');
INSERT INTO TBL_TASK_V3 (name) VALUES ('task 8');
INSERT INTO TBL_TASK_V3 (name) VALUES ('task 9');
INSERT INTO TBL_TASK_V3 (name) VALUES ('task 10');
INSERT INTO TBL_TASK_V3 (name) VALUES ('task 11');

UPDATE TBL_TASK_V3 SET PARENT_TASK = (SELECT id FROM TBL_TASK_V3 WHERE name = 'task 1') WHERE name = 'task 2';
UPDATE TBL_TASK_V3 SET PARENT_TASK = (SELECT id FROM TBL_TASK_V3 WHERE name = 'task 2') WHERE name = 'task 3';
UPDATE TBL_TASK_V3 SET PARENT_TASK = (SELECT id FROM TBL_TASK_V3 WHERE name = 'task 3') WHERE name = 'task 4';
UPDATE TBL_TASK_V3 SET PARENT_TASK = (SELECT id FROM TBL_TASK_V3 WHERE name = 'task 4') WHERE name = 'task 5';
UPDATE TBL_TASK_V3 SET PARENT_TASK = (SELECT id FROM TBL_TASK_V3 WHERE name = 'task 5') WHERE name = 'task 6';
UPDATE TBL_TASK_V3 SET PARENT_TASK = (SELECT id FROM TBL_TASK_V3 WHERE name = 'task 6') WHERE name = 'task 7';
UPDATE TBL_TASK_V3 SET PARENT_TASK = (SELECT id FROM TBL_TASK_V3 WHERE name = 'task 7') WHERE name = 'task 8';
UPDATE TBL_TASK_V3 SET PARENT_TASK = (SELECT id FROM TBL_TASK_V3 WHERE name = 'task 8') WHERE name = 'task 9';
UPDATE TBL_TASK_V3 SET PARENT_TASK = (SELECT id FROM TBL_TASK_V3 WHERE name = 'task 9') WHERE name = 'task 10';
UPDATE TBL_TASK_V3 SET PARENT_TASK = (SELECT id FROM TBL_TASK_V3 WHERE name = 'task 10') WHERE name = 'task 11';

SELECT * FROM TBL_TASK_V3 ttv ;
SELECT * FROM TBL_USER_V3 tuv ;
SELECT * FROM TBL_USER_TASK ;

INSERT INTO TBL_USER_TASK (TASK_ID, TASK_ASSIGNEE) values ((SELECT id FROM TBL_TASK_V3 WHERE NAME = 'task 2'),
(SELECT id FROM TBL_USER_V3 WHERE email_address = 'user1@email.com'));
INSERT INTO TBL_USER_TASK (TASK_ID, TASK_ASSIGNEE) values ((SELECT id FROM TBL_TASK_V3 WHERE NAME = 'task 3'),
(SELECT id FROM TBL_USER_V3 WHERE email_address = 'user1@email.com'));
INSERT INTO TBL_USER_TASK (TASK_ID, TASK_ASSIGNEE) values ((SELECT id FROM TBL_TASK_V3 WHERE NAME = 'task 4'),
(SELECT id FROM TBL_USER_V3 WHERE email_address = 'user2@email.com'));
INSERT INTO TBL_USER_TASK (TASK_ID, TASK_ASSIGNEE) values ((SELECT id FROM TBL_TASK_V3 WHERE NAME = 'task 5'),
(SELECT id FROM TBL_USER_V3 WHERE email_address = 'user2@email.com'));
INSERT INTO TBL_USER_TASK (TASK_ID, TASK_ASSIGNEE) values ((SELECT id FROM TBL_TASK_V3 WHERE NAME = 'task 6'),
(SELECT id FROM TBL_USER_V3 WHERE email_address = 'user3@email.com'));
INSERT INTO TBL_USER_TASK (TASK_ID, TASK_ASSIGNEE) values ((SELECT id FROM TBL_TASK_V3 WHERE NAME = 'task 7'),
(SELECT id FROM TBL_USER_V3 WHERE email_address = 'user3@email.com'));
INSERT INTO TBL_USER_TASK (TASK_ID, TASK_ASSIGNEE) values ((SELECT id FROM TBL_TASK_V3 WHERE NAME = 'task 8'),
(SELECT id FROM TBL_USER_V3 WHERE email_address = 'user4@email.com'));
INSERT INTO TBL_USER_TASK (TASK_ID, TASK_ASSIGNEE) values ((SELECT id FROM TBL_TASK_V3 WHERE NAME = 'task 9'),
(SELECT id FROM TBL_USER_V3 WHERE email_address = 'user4@email.com'));
INSERT INTO TBL_USER_TASK (TASK_ID, TASK_ASSIGNEE) values ((SELECT id FROM TBL_TASK_V3 WHERE NAME = 'task 10'),
(SELECT id FROM TBL_USER_V3 WHERE email_address = 'user1@email.com'));
INSERT INTO TBL_USER_TASK (TASK_ID, TASK_ASSIGNEE) values ((SELECT id FROM TBL_TASK_V3 WHERE NAME = 'task 11'),
(SELECT id FROM TBL_USER_V3 WHERE email_address = 'user2@email.com'));
INSERT INTO TBL_USER_TASK (TASK_ID, TASK_ASSIGNEE) values ((SELECT id FROM TBL_TASK_V3 WHERE NAME = 'task 1'),
(SELECT id FROM TBL_USER_V3 WHERE email_address = 'user3@email.com'));

select PK_TABLE.* from tbl_task_v3 PK_TABLE
inner join tbl_user_task FK_TABLE on PK_TABLE.id = FK_TABLE.task_id
where FK_TABLE.task_assignee =
(SELECT id FROM TBL_USER_V3 tuv WHERE TUV.EMAIL_ADDRESS = 'user3@email.com');

select PK_TABLE.* from tbl_task_v3 PK_TABLE
inner join tbl_task_user FK_TABLE on FK_TABLE.task_id = PK_TABLE.id
where FK_TABLE.task_assignee = ?

drop table if exists tbl_user_task_v4;
drop table if exists tbl_task_v4;
drop table if exists tbl_user_v4;

create table if not exists tbl_user_v4 (
  email_address varchar(100) not null,
  username varchar(50) not null,
  nickname varchar(50),
  date_joined timestamp not null default now(),
  access_level varchar default 'VISITOR',
  addr_city varchar(50),
  addr_state varchar(30),
  addr_postal_code varchar(10),
  constraint customer_pk_v4 primary key(email_address, username)
);

create table if not exists tbl_task_v4 (
  num int NOT null,
  name varchar(50) not null,
  done boolean not null default false,
  task_created timestamp not null default now(),
  parent_task_num int,
  parent_task_name varchar(50),
  constraint task_pk_v4 primary key(num, name),
  constraint task_parent_fk_v4 foreign key(parent_task_num, parent_task_name) references tbl_task_v2(num, name)
);

create table if not exists tbl_user_task_v4 (
    task_num int not null,
    task_name varchar(50) not NULL,
    task_assignee_email varchar(100) not null,
    task_assignee_uname varchar(50) NOT null,
    CONSTRAINT task_fk_v4 FOREIGN key(task_num, task_name) REFERENCES tbl_task_v4(num, name),
  	CONSTRAINT task_assignee_fk_v4 FOREIGN key(task_assignee_email, task_assignee_uname) REFERENCES tbl_user_v4(email_address, username)
);

INSERT INTO TBL_USER_v4 (email_address, username, NICKNAME) VALUES ('user1@email.com', 'one', 'user one');
INSERT INTO TBL_USER_v4 (email_address, username, NICKNAME) VALUES ('user2@email.com', 'two', 'user two');
INSERT INTO TBL_USER_v4 (email_address, username, NICKNAME) VALUES ('user3@email.com', 'three', 'user three');
INSERT INTO TBL_USER_v4 (email_address, username, NICKNAME) VALUES ('user4@email.com', 'four', 'user four');
INSERT INTO TBL_USER_v4 (email_address, username, NICKNAME) VALUES ('user5@email.com', 'five', 'user five');

INSERT INTO TBL_TASK_v4 (name, num) VALUES ('task 1', 1);
INSERT INTO TBL_TASK_v4 (name, num) VALUES ('task 2', 2);
INSERT INTO TBL_TASK_v4 (name, num) VALUES ('task 3', 3);
INSERT INTO TBL_TASK_v4 (name, num) VALUES ('task 4', 4);
INSERT INTO TBL_TASK_v4 (name, num) VALUES ('task 5', 5);
INSERT INTO TBL_TASK_v4 (name, num) VALUES ('task 6', 6);
INSERT INTO TBL_TASK_v4 (name, num) VALUES ('task 7', 7);
INSERT INTO TBL_TASK_v4 (name, num) VALUES ('task 8', 8);
INSERT INTO TBL_TASK_v4 (name, num) VALUES ('task 9', 9);
INSERT INTO TBL_TASK_v4 (name, num) VALUES ('task 10', 10);
INSERT INTO TBL_TASK_v4 (name, num) VALUES ('task 11', 11);

UPDATE TBL_TASK_v4 SET PARENT_TASK_NAME = 'task 1', PARENT_TASK_NUM = 1 WHERE name = 'task 2' AND num = 2;
UPDATE TBL_TASK_v4 SET PARENT_TASK_NAME = 'task 2', PARENT_TASK_NUM = 2  WHERE name = 'task 3' AND num = 3;
UPDATE TBL_TASK_v4 SET PARENT_TASK_NAME = 'task 3', PARENT_TASK_NUM = 3  WHERE name = 'task 4' AND num = 4;
UPDATE TBL_TASK_v4 SET PARENT_TASK_NAME = 'task 4', PARENT_TASK_NUM = 4  WHERE name = 'task 5' AND num = 5;
UPDATE TBL_TASK_v4 SET PARENT_TASK_NAME = 'task 5', PARENT_TASK_NUM = 5  WHERE name = 'task 6' AND num = 6;
UPDATE TBL_TASK_v4 SET PARENT_TASK_NAME = 'task 6', PARENT_TASK_NUM = 6  WHERE name = 'task 7' AND num = 7;
UPDATE TBL_TASK_v4 SET PARENT_TASK_NAME = 'task 7', PARENT_TASK_NUM = 7  WHERE name = 'task 8' AND num = 8;
UPDATE TBL_TASK_v4 SET PARENT_TASK_NAME = 'task 8', PARENT_TASK_NUM = 8  WHERE name = 'task 9' AND num = 9;
UPDATE TBL_TASK_v4 SET PARENT_TASK_NAME = 'task 9', PARENT_TASK_NUM = 9  WHERE name = 'task 10' AND num = 10;
UPDATE TBL_TASK_v4 SET PARENT_TASK_NAME = 'task 10', PARENT_TASK_NUM = 10  WHERE name = 'task 11' AND num = 11;

INSERT INTO TBL_USER_TASK_v4 (TASK_NUM, TASK_NAME, TASK_ASSIGNEE_EMAIL, TASK_ASSIGNEE_UNAME) values (2, 'task 2', 'user1@email.com', 'one');
INSERT INTO TBL_USER_TASK_v4 (TASK_NUM, TASK_NAME, TASK_ASSIGNEE_EMAIL, TASK_ASSIGNEE_UNAME) values (3, 'task 3', 'user1@email.com', 'one');
INSERT INTO TBL_USER_TASK_v4 (TASK_NUM, TASK_NAME, TASK_ASSIGNEE_EMAIL, TASK_ASSIGNEE_UNAME) values (4, 'task 4', 'user2@email.com', 'two');
INSERT INTO TBL_USER_TASK_v4 (TASK_NUM, TASK_NAME, TASK_ASSIGNEE_EMAIL, TASK_ASSIGNEE_UNAME) values (5, 'task 5', 'user2@email.com', 'two');
INSERT INTO TBL_USER_TASK_v4 (TASK_NUM, TASK_NAME, TASK_ASSIGNEE_EMAIL, TASK_ASSIGNEE_UNAME) values (6, 'task 6', 'user3@email.com', 'three');
INSERT INTO TBL_USER_TASK_v4 (TASK_NUM, TASK_NAME, TASK_ASSIGNEE_EMAIL, TASK_ASSIGNEE_UNAME) values (7, 'task 7', 'user3@email.com', 'three');
INSERT INTO TBL_USER_TASK_v4 (TASK_NUM, TASK_NAME, TASK_ASSIGNEE_EMAIL, TASK_ASSIGNEE_UNAME) values (8, 'task 8', 'user4@email.com', 'four');
INSERT INTO TBL_USER_TASK_v4 (TASK_NUM, TASK_NAME, TASK_ASSIGNEE_EMAIL, TASK_ASSIGNEE_UNAME) values (9, 'task 9', 'user4@email.com', 'four');
INSERT INTO TBL_USER_TASK_v4 (TASK_NUM, TASK_NAME, TASK_ASSIGNEE_EMAIL, TASK_ASSIGNEE_UNAME) values (10, 'task 10', 'user1@email.com', 'one');
INSERT INTO TBL_USER_TASK_v4 (TASK_NUM, TASK_NAME, TASK_ASSIGNEE_EMAIL, TASK_ASSIGNEE_UNAME) values (11, 'task 11', 'user2@email.com', 'two');
INSERT INTO TBL_USER_TASK_v4 (TASK_NUM, TASK_NAME, TASK_ASSIGNEE_EMAIL, TASK_ASSIGNEE_UNAME) values (1, 'task 1', 'user3@email.com', 'three');

SELECT * FROM TBL_USER_TASK_V4 tutv;