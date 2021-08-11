DROP TABLE IF EXISTS tbl_tasks;

create table if not exists tbl_task (
  id UUID default random_uuid(),
  name varchar(50) not null,
  done boolean not null default false,
  task_created timestamp not null default now(),
  constraint task_pk primary key(id),
  constraint uniq_name unique (name)
);