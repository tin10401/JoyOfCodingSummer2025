package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CoverageBoosterIT {

  @Test
  void dumperHitsAllBranches() throws Exception {
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

  @Test
  void prettyPrinterHitsEveryLine() throws Exception {
    AppointmentBook book = new AppointmentBook("Bob");
    book.addAppointment(new Appointment(
        "Breakfast",
        LocalDateTime.of(2025, 1, 1, 8, 0),
        LocalDateTime.of(2025, 1, 1, 9, 0)));
    book.addAppointment(new Appointment(
        "Stand-up",
        LocalDateTime.of(2025, 1, 1, 9, 30),
        LocalDateTime.of(2025, 1, 1, 9, 45)));
    book.addAppointment(new Appointment(
        "Lunch",
        LocalDateTime.of(2025, 1, 1, 12, 0),
        LocalDateTime.of(2025, 1, 1, 13, 0)));

    StringWriter sw = new StringWriter();
    new PrettyPrinter(sw).dump(book);

    String out = sw.toString().toLowerCase();

    assertTrue(out.contains("owner: bob"));
    assertTrue(out.contains("appointments") && out.contains("3"));
  }

  @Test
  void parserAndDumperRoundTripLarge() throws Exception {
    StringBuilder seed = new StringBuilder("Bob\n");
    for (int i = 0; i < 20; i++) {
      seed.append("Meet-")
          .append(i).append('|')
          .append(String.format("01/%02d/2025 10:00 AM|01/%02d/2025 10:30 AM%n", i + 2, i + 2));
    }

    AppointmentBook fromFile =
        new TextParser(new StringReader(seed.toString())).parse();

    StringWriter sw = new StringWriter();
    new TextDumper(sw).dump(fromFile);

    AppointmentBook reparsed =
        new TextParser(new StringReader(sw.toString())).parse();

    assertEquals(20, reparsed.getAppointments().size());
  }
}

