package org.neikius.test.coursarium.repository.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@Data
public class Course {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  @Lob
  private String description;

  private Integer participantsLimit;

}
