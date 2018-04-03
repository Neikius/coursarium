package org.neikius.test.coursarium.repository.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Student {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  private String token;

  @ManyToMany
  private List<Course> courses = new ArrayList<>();

}
