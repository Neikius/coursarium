package org.neikius.test.coursarium;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neikius.test.coursarium.controller.CourseController;
import org.neikius.test.coursarium.controller.StudentController;
import org.neikius.test.coursarium.repository.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTest {

  @Autowired
  private CourseController courseController;

  @Test
  public void testGetCourses() {
    String name = "Programiranje 1";
    List<Course> courses = courseController.getCoursesSearch(name);
    assertThat(courses.size()).isEqualTo(1);
    assertThat(courses).extracting("name").contains(name);
  }

  @Autowired
  private StudentController studentController;

  @Test
  @WithMockUser(username = "Nejc")
  @Transactional
  public void testEnroll() {
    studentController.enrollCourse(1L, 1L);
    List<Course> courses = studentController.getEnrolledCourses(1L);
    assertThat(courses.size()).isEqualTo(1);
    assertThat(courses).extracting("id").contains(1L);

    studentController.cancelCourseEnrollment(1L, 1L);

    List<Course> courses2 = studentController.getEnrolledCourses(1L);
    assertThat(courses.size()).isEqualTo(0);
  }

}
