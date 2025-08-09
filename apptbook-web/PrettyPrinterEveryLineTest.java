package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;
import java.io.StringWriter;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PrettyPrinterEveryLineTest {
  @Test
  void prettyPrintHitsAll() {
    AppointmentBook b = new AppointmentBook("Z");
    b.addAppointment(new Appointment("C",
        LocalDateTime.of(2025, 3, 3, 15, 0),
        LocalDateTime.of(2025, 3, 3, 15, 30)));
    StringWriter w = new StringWriter();
    new PrettyPrinter(w).dump(b);
    String out = w.toString();
    assertTrue(out.contains("Owner: Z"));
    assertTrue(out.contains("appointments"));
    assertTrue(out.contains("From"));
  }
}

