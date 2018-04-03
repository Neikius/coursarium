package org.neikius.test.coursarium.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.neikius.test.coursarium.controller.exception.ResourceNotFoundException;
import org.neikius.test.coursarium.repository.CourseRepository;
import org.neikius.test.coursarium.repository.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
@RequestMapping("/v1/course")
public class CourseController {

  @Autowired
  private CourseRepository courseRepository;

  @ApiOperation("Get all courses")
  @GetMapping("all")
  public List<Course> getCourses() {
    List<Course> courses = courseRepository.findAll();
    if (courses.size() == 0) {
      throw new ResourceNotFoundException();
    }
    return courses;
  }

  @ApiOperation("Find courses")
  @GetMapping("find")
  public List<Course> getCoursesSearch(String term) {
    List<Course> courses = courseRepository.getCourseByNameIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(term, term);
    if (courses == null || courses.size() == 0) {
      throw new ResourceNotFoundException();
    }
    return courses;
  }

  @ApiOperation("Add course, returns the ID of inserted course")
  @PutMapping("/")
  public Long addCourse(Course course) {
    return courseRepository.save(course).getId();
  }

}
