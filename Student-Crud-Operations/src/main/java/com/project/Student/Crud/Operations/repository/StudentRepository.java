package com.project.Student.Crud.Operations.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.Student.Crud.Operations.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
