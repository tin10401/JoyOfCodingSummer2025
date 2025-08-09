package edu.pdx.cs.joy.tin22;

import java.time.LocalDateTime;

public class Appointment extends AbstractAppointment {
  private final String description;
  private final LocalDateTime beginTime;
  private final LocalDateTime endTime;

  public Appointment(String description, LocalDateTime beginTime, LocalDateTime endTime) {
    this.description = description;
    this.beginTime = beginTime;
    this.endTime = endTime;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public LocalDateTime getBeginTime() {
    return beginTime;
  }

  @Override
  public LocalDateTime getEndTime() {
    return endTime;
  }
}

