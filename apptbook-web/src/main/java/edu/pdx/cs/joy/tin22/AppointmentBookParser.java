package edu.pdx.cs.joy.tin22;

public interface AppointmentBookParser<T extends AbstractAppointmentBook<?>> {
  T parse();
}

