package com.project.Student.Crud.Operations.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.Student.Crud.Operations.entity.Student;
import com.project.Student.Crud.Operations.exception.StudentNotFoundException;
import com.project.Student.Crud.Operations.repository.StudentRepository;

@RestController
public class StudentController {
	
	@Autowired
	StudentRepository studentRepository;
	
	//get all the students
	//https://localhost:8080/students
	@GetMapping("/students")
	public ResponseEntity<List<Student>> getAllStudents() {
		List<Student> students = studentRepository.findAll();
		if(students.size() <= 0) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/students/{id}")
	public Student getStudent(@PathVariable int id) throws StudentNotFoundException {
		Optional<Student> student = studentRepository.findById(id);
		if(student.isEmpty()) {  //null will not return this message because its empty(meaning not present in the database table)
			throw new StudentNotFoundException("Student with ID " + id + " not found");
		}
		else {
			return student.get();
		}
		
	}
	
	@PostMapping("/student/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void createStudent(@RequestBody Student student) {
		studentRepository.save(student);
	}
	
	@PutMapping("/student/update/{id}")
	public ResponseEntity<Student> updateStudents(@PathVariable int id) {
		try {
			Student student = studentRepository.findById(id).get();
			student.setName("Yogesh");
			student.setPercentage(69);
			studentRepository.save(student);
			return ResponseEntity.ok().body(student);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
	
//	@DeleteMapping("/student/delete/{id}")
//	public void deleteStudent(@PathVariable int id) {
//		studentRepository.deleteById(id);
//	}

//	OR
	
	@DeleteMapping("/student/delete/{id}")
	public ResponseEntity<Student> deleteStudent(@PathVariable int id) {
		try {
			Student student = studentRepository.findById(id).get();
			studentRepository.delete(student);
//			return ResponseEntity.ok(student);
			return ResponseEntity.ok().body(student);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
		
	}
}
