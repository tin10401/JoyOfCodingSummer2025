package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CoverageBoostTest {

  @Test
  void abstractBookCore() {
    AppointmentBook book = new AppointmentBook("Owner");
    Appointment a = new Appointment(
        "Meeting",
        LocalDateTime.of(2025, 8, 7, 9, 0),
        LocalDateTime.of(2025, 8, 7, 10, 0));
    book.addAppointment(a);

    assertEquals("Owner", book.getOwnerName());
    assertEquals(1, book.getAppointments().size());
    assertTrue(book.getAppointments().contains(a));
  }

  @Test
  void parserAndDumperRoundTrip() throws Exception {
    AppointmentBook book = new AppointmentBook("Jack");
    book.addAppointment(new Appointment(
        "A",
        LocalDateTime.of(2025, 2, 2, 10, 0),
        LocalDateTime.of(2025, 2, 2, 11, 0)));

    StringWriter sw = new StringWriter();
    new TextDumper(sw).dump(book);

    AppointmentBook reparsed =
        new TextParser(new StringReader(sw.toString())).parse();

    assertEquals(book.getOwnerName(), reparsed.getOwnerName());
    assertEquals(1, reparsed.getAppointments().size());
  }
}

