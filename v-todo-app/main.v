module main

import nedpals.vex.server
import nedpals.vex.router
import nedpals.vex.ctx
import dbrepo {Task, TaskRepo}

fn create_new_task(mut repo TaskRepo, req &ctx.Req, mut resp ctx.Resp) {
	form_data := req.parse_form() or {
		map[string]string{}
	}
	name := form_data['name']
	println('create with name $name')
	new_task := Task{name, false}
	repo.save(new_task) or {
		resp.send("problem creating new task", 400)
		return
	}
	resp.send('Created', 201)
}

fn find_task_by_name(mut repo TaskRepo, req &ctx.Req, mut resp ctx.Resp) {
	name := req.params['name']
	println('find by name $name')
	task := repo.find(name) or {
		resp.send("problem fetching task", 400)
		return
	}
	resp.send_json(task, 200)
}

fn find_all_tasks(mut repo TaskRepo, req &ctx.Req, mut resp ctx.Resp) {
	println('find all tasks')
	tasks := repo.find_all() or {
		resp.send("problem fetching tasks", 400)
		return
	}
	resp.send_json(tasks, 200)
}

fn find_all_matches(mut repo TaskRepo, req &ctx.Req, mut resp ctx.Resp) {
	println('find all tasks matching criteria')
	criteria := fn (task &Task) bool {
		return !task.done
	}
	tasks := repo.find_all_maches(criteria) or {
		resp.send("problem fetching tasks", 400)
		return
	}
	resp.send_json(tasks, 200)
}

fn toggle_task_status(mut repo TaskRepo, req &ctx.Req, mut resp ctx.Resp) {
	println('toggle tasks with matching name')
	form_data := req.parse_form() or {
		map[string]string{}
	}
	name := form_data['name']
	done := form_data['done']
	status := repo.toggle(name, done == 'true') or {
		resp.send("problem updating tasks", 400)
		return
	}
	resp.send_json(status, 200)
}

fn delete_task(mut repo TaskRepo, req &ctx.Req, mut resp ctx.Resp) {
	println('delete tasks with matching name')
	name := req.params['name']
	tasks := repo.delete(name) or {
		resp.send("problem deleting tasks", 400)
		return
	}
	resp.send_json(tasks, 200)
}


fn main() {
	//initialize the app
	mut app := router.new()
	mut repo := &TaskRepo{}
	app.inject(repo)

	app.route(.get, '/', fn (req &ctx.Req, mut resp ctx.Resp) {
		resp.send('You have reached Vex!', 200)
	})

	app.route(.post, '/task', fn (req &ctx.Req, mut resp ctx.Resp) {
		mut ctx_repo := req.ctx
		create_new_task(mut ctx_repo, req, mut resp)
	})

	app.route(.get, '/task/name/:name', fn (req &ctx.Req, mut resp ctx.Resp) {
		mut ctx_repo := req.ctx
		find_task_by_name(mut ctx_repo, req, mut resp)
	})

	app.route(.get, '/task/all', fn (req &ctx.Req, mut resp ctx.Resp) {
		mut ctx_repo := req.ctx
		find_all_tasks(mut ctx_repo, req, mut resp)
	})
	
	app.route(.get, '/task', fn (req &ctx.Req, mut resp ctx.Resp) {
		mut ctx_repo := req.ctx
		find_all_matches(mut ctx_repo, req, mut resp)
	})

	app.route(.put, '/task', fn (req &ctx.Req, mut resp ctx.Resp) {
		mut ctx_repo := req.ctx
		toggle_task_status(mut ctx_repo, req, mut resp)
	})

	app.route(.delete, '/task/:name', fn (req &ctx.Req, mut resp ctx.Resp) {
		mut ctx_repo := req.ctx
		delete_task(mut ctx_repo, req, mut resp)
	})

	//start the app
	server.serve(app, 8081)
}
