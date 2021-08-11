module dbrepo

import sqlite

pub struct Task {
pub:
	name string
pub mut:
	done bool
}

interface Repo {
	save(task &Task) ?int
	find(name string) ?Task
	find_all() ?[]Task
	find_all_maches(fn (&Task) bool) ?[]Task
	toggle(name string) ?int
	delete(name string) ?int
}

pub struct TaskRepo {
	name string
}

pub fn(mut repo TaskRepo) connect() ?sqlite.DB {
	db := sqlite.connect('./todos.db') or { 
		return error('could not connect to repository')
	}
	return db
}

pub fn (mut repo TaskRepo) init() {
	db := repo.connect() or {
		panic(err)
	}
	db.exec('drop table if exists tasks;')
	db.exec('create table if not exists tasks (name name not null, done bool default 0);')
	println('database initialized')
}

pub fn (mut repo TaskRepo) save(task &Task) ?int {
	db := repo.connect()?
	_, b := db.exec("insert into tasks (name) values ('$task.name');") 
	println('saved result $b')
	return b
}

pub fn (mut repo TaskRepo) find(name string) ?Task {
	db := repo.connect()?
	row := db.exec_one("select * from tasks where name = '$name' limit 1;")?
	println('find result $row')
	return Task{row.vals[0], row.vals[1] == '1'}
}

pub fn (mut repo TaskRepo) find_all() ?[]Task {
	db := repo.connect()?
	rows, _ := db.exec('select * from tasks;')
	return rows.map(row_to_task)
}

pub fn (mut repo TaskRepo) find_all_maches(apply fn (&Task) bool) ?[]Task {
	db := repo.connect()?
	rows, _ := db.exec('select * from tasks;')
	return rows.map(row_to_task).filter(apply(it))
}

pub fn (mut repo TaskRepo) toggle(name string, done bool) ?int {
	db := repo.connect()?
	_, status := db.exec("update tasks set done = ${done} where name = '${name}';")
	return status
}

pub fn (mut repo TaskRepo) delete(name string) ?int {
	db := repo.connect()?
	println('delete task with name $name')
	_, status := db.exec("delete from tasks where name = '$name';")
	return status
}

fn row_to_task (row sqlite.Row) Task {
	return Task{row.vals[0], row.vals[1] == '1'}
}

