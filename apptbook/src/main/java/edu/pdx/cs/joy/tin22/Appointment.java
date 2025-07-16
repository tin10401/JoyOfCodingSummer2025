package edu.pdx.cs.joy.tin22;

import edu.pdx.cs.joy.AbstractAppointment;

public class Appointment extends AbstractAppointment {
  private String description;
  private String beginTime;
  private String endTime;

  public Appointment() {
  }

  public Appointment(String description, String beginTime, String endTime) {
    if (description == null || description.trim().isEmpty()) {
      throw new IllegalArgumentException("Description is missing or empty");
    }
    this.description = description;
    this.beginTime   = beginTime;
    this.endTime     = endTime;
  }

  @Override
  public String getDescription() {
    if (description == null) {
      return "This method is not implemented yet";
    }
    return description;
  }

  @Override
  public String getBeginTimeString() {
    if (beginTime == null) {
      throw new UnsupportedOperationException("This method is not implemented yet");
    }
    return beginTime;
  }

  @Override
  public String getEndTimeString() {
    if (endTime == null) {
      throw new UnsupportedOperationException("This method is not implemented yet");
    }
    return endTime;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setBeginTimeString(String beginTime) {
    this.beginTime = beginTime;
  }

  public void setEndTimeString(String endTime) {
    this.endTime = endTime;
  }
}

