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
	public ResponseEntity<Student> getStudent(@PathVariable int id) {
		try {
			 Student student = studentRepository.findById(id).get();
			 return ResponseEntity.ok(student);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PostMapping("/student/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void createStudent(@RequestBody Student student) {
		studentRepository.save(student);
	}
	
	@PutMapping("/student/update/{id}")
	public Student updateStudents(@PathVariable int id) {
		Student student = studentRepository.findById(id).get();
		student.setName("Yogesh");
		student.setPercentage(69);
		studentRepository.save(student);
		return student;
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
