module main
import dbrepo {Task, TaskRepo}

fn test_repo() {
	mut repo := TaskRepo{}
	eat := Task{'eat', false}
	live := Task{'live', false}
	laugh := Task{'laugh', false}

	r1 := repo.save(eat)
	println('r is $r1')

	r2 := repo.save(live)
	println('r is $r2')

	r3 := repo.save(laugh)
	println('r is $r3')

	println('find task')

	by_name := repo.find('eat') or {
		return
	}
	println(by_name)

	all := repo.find_all(fn (task &Task) bool {
		return task.name == 'laugh'
	}) or {
		return
	}
	println(all)
	
	finish_eating := repo.toggle('eat') or {
		return
	}
	println(finish_eating)

	drop_live := repo.delete('live') or {
		return
	}
	println('dropped $drop_live')
	println('exiting')
}