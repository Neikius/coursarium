package org.neikius.test.coursarium.repository;

import org.neikius.test.coursarium.repository.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, Long> {

  List<Course> getCoursesByName(String name);

  List<Course> getCourseByNameIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(String name, String description);

}
