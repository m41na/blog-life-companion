#!/usr/bin/env -S v run

// Remove if build/ exits, ignore any errors if it doesn't
rmdir_all('build') or { }

// Create build/, never fails as build/ does not exist
mkdir('build') ?

// Move *.v files to build/
result := execute('mv *.exe build/')
if result.exit_code != 0 {
	println(result.output)
}