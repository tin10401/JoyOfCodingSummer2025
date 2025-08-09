package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class AdditionalCoverageTest {

  @Test
  void dumpParsePrettyRoundTrip() {
    AppointmentBook book = new AppointmentBook("Owner");
    book.addAppointment(new Appointment("Appt",
        LocalDateTime.of(2025, 1, 1, 15, 0),
        LocalDateTime.of(2025, 1, 1, 16, 0)));

    StringWriter sw = new StringWriter();
    new TextDumper(sw).dump(book);
    String roundTrip = sw.toString();

    AppointmentBook parsed = new TextParser(new StringReader(roundTrip)).parse();
    assertNotNull(parsed);
    assertEquals(1, parsed.getAppointments().size());
  }

  @Test
  void basicDomainModelSmoke() {
    Appointment a = new Appointment("X",
        LocalDateTime.of(2025, 1, 1, 15, 0),
        LocalDateTime.of(2025, 1, 1, 16, 0));
    AppointmentBook b = new AppointmentBook("O");
    b.addAppointment(a);
    assertEquals("O", b.getOwnerName());
    assertTrue(b.getAppointments().contains(a));
  }
}

