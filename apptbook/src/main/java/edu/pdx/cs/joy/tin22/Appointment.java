package edu.pdx.cs.joy.tin22;

import edu.pdx.cs.joy.AbstractAppointment;

public class Appointment extends AbstractAppointment {
  private String description;
  private String beginTime;
  private String endTime;

  public Appointment() { }

  public Appointment(String description, String beginTime, String endTime) {
    this.description = description;
    this.beginTime   = beginTime;
    this.endTime     = endTime;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public String getBeginTimeString() {
    return beginTime;
  }

  @Override
  public String getEndTimeString() {
    return endTime;
  }
}

