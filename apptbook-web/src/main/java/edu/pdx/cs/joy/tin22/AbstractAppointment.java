package edu.pdx.cs.joy.tin22;

import java.time.LocalDateTime;

public abstract class AbstractAppointment {
  public abstract String getDescription();
  public abstract LocalDateTime getBeginTime();
  public abstract LocalDateTime getEndTime();
}

