package org.example.study.exception;

public class NotOwnerException extends RuntimeException{
  //생성자
  public NotOwnerException(String message) {
    super(message);
  }

}