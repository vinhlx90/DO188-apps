package com.redhat.beeper.application.model;

import lombok.Data;

import java.util.Date;


@Data
public class Beep {


  private Long id;

  private final String username;

  private final String content;

  private Integer votes = 0;

  private Date creationDate;

  public void upvote() {
    votes++;
  }
  public void downvote() {
    votes--;
  }
}

