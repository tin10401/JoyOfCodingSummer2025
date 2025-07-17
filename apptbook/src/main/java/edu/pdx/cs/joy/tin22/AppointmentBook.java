package edu.pdx.cs.joy.tin22;

import edu.pdx.cs.joy.AbstractAppointmentBook;
import java.util.ArrayList;
import java.util.Collection;

public class AppointmentBook extends AbstractAppointmentBook<Appointment> {
  private final String owner;
  private final ArrayList<Appointment> appts = new ArrayList<>();

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
  public void addAppointment(Appointment appt) {
    appts.add(appt);
  }
}

