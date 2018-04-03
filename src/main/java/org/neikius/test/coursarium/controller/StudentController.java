package org.neikius.test.coursarium.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.neikius.test.coursarium.controller.exception.AccessForbiddenException;
import org.neikius.test.coursarium.controller.exception.ResourceNotFoundException;
import org.neikius.test.coursarium.repository.CourseRepository;
import org.neikius.test.coursarium.repository.StudentRepository;
import org.neikius.test.coursarium.repository.model.Course;
import org.neikius.test.coursarium.repository.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api
@RestController
@RequestMapping("/v1/student")
public class StudentController {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private CourseRepository courseRepository;

  @ApiOperation("Get student info")
  @GetMapping("/{studentId}")
  public Student getStudent(@PathVariable Long studentId) {
    return verifyStudentAccess(studentId);
  }

  @ApiOperation("Get enrolled courses")
  @GetMapping("/{studentId}/enrolled")
  public List<Course> getEnrolledCourses(@PathVariable Long studentId) {
    return verifyStudentAccess(studentId).getCourses();
  }

  @ApiOperation("Enroll in a course")
  @PutMapping("/{studentId}/enrolled/{courseId}")
  public void enrollCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
    Student student = verifyStudentAccess(studentId);
    courseRepository.findById(courseId).ifPresent(course -> {
      if (!student.getCourses().contains(course)) {
        student.getCourses().add(course);
        studentRepository.save(student);
      }
    });
  }

  @ApiOperation("Enroll in a course")
  @DeleteMapping("/{studentId}/enrolled/{courseId}")
  public void cancelCourseEnrollment(@PathVariable Long studentId, @PathVariable Long courseId) {
    Student student = verifyStudentAccess(studentId);
    courseRepository.findById(courseId).ifPresent(course -> {
      if (student.getCourses().remove(course))
        studentRepository.save(student);
    });
  }

  @ApiOperation("Add student")
  @PutMapping("/")
  public Long addStudent(Student student) {
    return studentRepository.save(student).getId();
  }

  /**
   * Verify if student is accessing his own data and return the student Entity
   * @param id
   * @return
   */
  private Student verifyStudentAccess(Long id) {
    String username = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

    Optional<Student> studentOptional = studentRepository.findById(id);
    Student student = studentOptional.orElseThrow(ResourceNotFoundException::new);
    if (!student.getName().equalsIgnoreCase(username)) {
      throw new AccessForbiddenException();
    }
    return student;
  }

}
