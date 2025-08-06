package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class CoreCoverageTest {

  @Test
  void roundTripAndExerciseUiClasses() throws Exception {
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");
    LocalDateTime begin = LocalDateTime.parse("10/01/2025 1:00 PM", fmt);
    LocalDateTime end   = LocalDateTime.parse("10/01/2025 2:00 PM", fmt);

    Appointment appt = new Appointment("Demo", begin, end);
    AppointmentBook book = new AppointmentBook("Alice");
    book.addAppointment(appt);

    StringWriter dumped = new StringWriter();
    new TextDumper(dumped).dump(book);

    AppointmentBook parsed =
        new TextParser(new StringReader(dumped.toString())).parse();
    assertEquals("Alice", parsed.getOwnerName());
    assertEquals(1, parsed.getAppointments().size());

    StringWriter pretty = new StringWriter();
    new PrettyPrinter(new PrintWriter(pretty)).dump(parsed);
    assertTrue(pretty.toString().contains("Alice"));

    Project4.main(new String[] { "-README" });
  }
}

