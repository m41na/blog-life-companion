module repo

pub struct Task {
pub:
	name string
mut:
	done bool
}

interface Repo {
	save(task &Task)
	find(name string) Task
	find_all(fn (&Task) bool) []Task
	toggle(name string) []Task
	delete(name string) int
}

pub struct TaskRepo {
	name string
mut:
	store []Task
}

pub fn (mut repo TaskRepo) save(task &Task) {
	repo.store << task
	println('current repo $repo.store')
}

pub fn (mut repo TaskRepo) find(name string) Task {
	return repo.store.filter(it.name == name).first()
}

pub fn (mut repo TaskRepo) find_all(apply fn (&Task) bool) []Task {
	return repo.store.filter(apply(it))
}

pub fn (mut repo TaskRepo) toggle(name string) []Task {
	repo.store = repo.store.map(toggle_task(name, it))
	return repo.store.filter(it.name == name)
}

pub fn (mut repo TaskRepo) delete(name string) int {
	size := repo.store.len
	repo.store = repo.store.filter(it.name != name)
	return size - repo.store.len
}

fn toggle_task(name string, task Task) Task {
	return match task.name {
		name { Task{task.name, !(task.done)} }
		else { task }
	}
}
