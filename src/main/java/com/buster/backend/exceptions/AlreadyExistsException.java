package com.buster.backend.exceptions;

public class AlreadyExistsException extends RuntimeException {
  private Integer code;

  public AlreadyExistsException(){}

    public AlreadyExistsException(String message) {
        super(message);
	this.code = 400;
    }

    public void setCode(Integer code) {
      this.code = code;
    }

    public Integer getCode() {
      return code;
    }
}
