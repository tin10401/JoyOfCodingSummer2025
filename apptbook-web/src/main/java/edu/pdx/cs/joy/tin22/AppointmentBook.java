package edu.pdx.cs.joy.tin22;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AppointmentBook implements AbstractAppointmentBook<Appointment> {
  private final String owner;
  private final List<Appointment> appts = new ArrayList<>();

  public AppointmentBook(String owner) {
    this.owner = owner;
  }

  @Override
  public String getOwnerName() {
    return owner;
  }

  @Override
  public Collection<Appointment> getAppointments() {
    return appts;
  }

  @Override
  public void addAppointment(Appointment a) {
    appts.add(a);
  }
}

