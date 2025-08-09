package edu.pdx.cs.joy.tin22;

import java.util.Collection;

public interface AbstractAppointmentBook<T extends AbstractAppointment> {
  String getOwnerName();
  Collection<T> getAppointments();
  void addAppointment(T appt);
}

