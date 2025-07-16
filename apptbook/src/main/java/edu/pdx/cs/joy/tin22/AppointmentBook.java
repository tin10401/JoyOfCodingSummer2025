package edu.pdx.cs.joy.tin22;

import edu.pdx.cs.joy.AbstractAppointmentBook;
import edu.pdx.cs.joy.AbstractAppointment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AppointmentBook extends AbstractAppointmentBook {
  private final String owner;
  private final List<Appointment> appointments = new ArrayList<>();

  public AppointmentBook(String owner) {
    if (owner == null || owner.trim().isEmpty()) {
      throw new IllegalArgumentException("Owner name is missing or empty");
    }
    this.owner = owner;
  }

  @Override
  public String getOwnerName() {
    return owner;
  }

  @Override
  public Collection<Appointment> getAppointments() {
    return Collections.unmodifiableList(appointments);
  }

  @Override
  public void addAppointment(AbstractAppointment appt) {
    if (!(appt instanceof Appointment)) {
      throw new IllegalArgumentException("Only Appointment instances allowed");
    }
    appointments.add((Appointment) appt);
  }
}
