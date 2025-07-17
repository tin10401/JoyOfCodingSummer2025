package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppointmentBookTest {

  @Test void addAndRetrieve() {
    AppointmentBook book = new AppointmentBook("Alice");
    Appointment appt = new Appointment("Lunch","01/01/2026 12:00","01/01/2026 13:00");
    book.addAppointment(appt);
    assertEquals("Alice", book.getOwnerName());
    assertTrue(book.getAppointments().contains(appt));
  }
}

