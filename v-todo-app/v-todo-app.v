module main
import repo {Task, TaskRepo}
import vweb
import json

fn by_filter (task &Task) bool {
	return task.name == 'laugh'
}

struct App {
	vweb.Context
mut:
	repo TaskRepo
}

fn main() {
	mut app := &App{
		repo: &TaskRepo{}
	}
	vweb.run(app, 8081)	
}

pub fn (mut app App) index() vweb.Result {
	message := 'Hello, world from Vweb!'
	return $vweb.html()
}

['/task'; post]
pub fn(mut app App) create_new_task() vweb.Result {
	name := app.form['name']
	new_task := &Task{name, false}
	app.repo.save(new_task)
	return app.ok('Created')
}

['/task'; get]
pub fn(mut app App) find_task_by_name() vweb.Result {
	name := app.query['name']
	println('find task by name $name')
	by_name := app.repo.find(name)
	println('found by name $by_name')
	json_result := json.encode(by_name)
	return app.json(json_result)
}