package com.buster.backend.common;

public class ApiResponse{
    private String status = "success";
    private String message = "Peticion exitosa";
    private Object data = null;
    private Integer code = 200;

    public void setStatus(String status) {
      this.status = status;
    }

    public String getStatus() {
      return status;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public String getMessage() {
      return message;
    }

    public void setData(Object data) {
      this.data = data;
    }

    public Object getData() {
      return data;
    }

    public void setCode(Integer code) {
      this.code = code;
    }

    public Integer getCode() {
      return code;
    }

    public ApiResponse() {
    }

    public ApiResponse(String status, Object data) {
      this.status = status;
      this.data = data;
    }

    public ApiResponse(String status, String message){
      this.status = status;
      this.message=message;
    }

    public ApiResponse(String status, String message, Object data){
      this.status = status;
      this.message = message;
      this.code = status == "error" ? 500 : this.code;
      this.data = data;
    }

    public ApiResponse(String status, String message, Integer code){
      this.status = status;
      this.message = message;
      this.code = code;
    }

    public ApiResponse(String status, String message, Integer code, Object data){
      this.status = status;
      this.message = message;
      this.code = code;
      this.data = data;
    }
}
