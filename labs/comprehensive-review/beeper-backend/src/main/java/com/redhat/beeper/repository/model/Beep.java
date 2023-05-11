package com.redhat.beeper.repository.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;


@Entity
@NoArgsConstructor
@Data
public class Beep   {

  @Id @GeneratedValue
  private Long id;

  private String username;

  private String content;

  private Integer votes;

  private Date creationDate;

}

