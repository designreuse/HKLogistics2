package com.hk.logistics.service.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rimal
 */
public abstract class AbstractGenericResponse extends JSONObject {

    @JsonProperty(DtoJsonConstants.EXCEPTION)
  protected boolean exception = false;
    @JsonProperty(DtoJsonConstants.MESSAGES)
  protected List<String> messages = new ArrayList<String>();

  public boolean isException() {
    return exception;
  }

  public AbstractGenericResponse setException(boolean exception) {
    this.exception = exception;
    return this;
  }

  public AbstractGenericResponse addMessage(String message) {
    this.messages.add(message);
    return this;
  }

  public AbstractGenericResponse addMessages(List<String> messages) {
    this.messages.addAll(messages);
    return this;
  }

  public List<String> getMessages() {
    return messages;
  }



  @Override
  protected List<String> getKeys() {
    List<String> keys = new ArrayList<String>(0);
    keys.add(DtoJsonConstants.MESSAGES);
    keys.add(DtoJsonConstants.EXCEPTION);

    return keys;
  }

  @Override
  protected List<Object> getValues() {
    List<Object> values = new ArrayList<Object>(0);
    values.add(this.messages);
    values.add(this.exception);
    return values;
  }
}
