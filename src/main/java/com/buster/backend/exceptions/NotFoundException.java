package com.buster.backend.exceptions;

public class NotFoundException extends RuntimeException {
    
    private Integer code;

    public NotFoundException(String message) {
        super(message);
	this.code=404;
    }

    public NotFoundException() {

    }

    public void setCode(Integer code) {
      this.code = code;
    }

    public Integer getCode() {
      return code;
    }
}
