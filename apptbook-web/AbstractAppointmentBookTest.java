package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AbstractAppointmentBookTest {
  @Test
  void addAndFetch() {
    AppointmentBook b = new AppointmentBook("X");
    b.addAppointment(new Appointment("A",
        LocalDateTime.of(2025, 1, 1, 9, 0),
        LocalDateTime.of(2025, 1, 1, 10, 0)));
    assertEquals("X", b.getOwnerName());
    assertEquals(1, b.getAppointments().size());
  }
}

