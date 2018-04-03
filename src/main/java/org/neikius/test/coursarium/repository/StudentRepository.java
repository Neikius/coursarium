package org.neikius.test.coursarium.repository;

import org.neikius.test.coursarium.repository.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
