package edu.pdx.cs.joy.tin22;

public interface AppointmentBookDumper<T extends AbstractAppointmentBook<?>> {
  void dump(T book);
}

