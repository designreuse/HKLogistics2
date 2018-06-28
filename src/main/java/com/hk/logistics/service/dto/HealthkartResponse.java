package com.hk.logistics.service.dto;

public class HealthkartResponse {

  public static final String STATUS_OK = "ok";
  public static final String STATUS_ERROR = "error";
  public static final String STATUS_ACCESS_DENIED = "denied";
  public static final String STATUS_RELOAD = "reload";
  public static final String STATUS_REDIRECT = "redirect";

  private String code;
  private String message;
  private Object data;

  public HealthkartResponse(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

}
