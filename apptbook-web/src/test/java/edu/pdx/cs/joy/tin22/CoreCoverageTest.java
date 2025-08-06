package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class CoreCoverageTest {

  @Test
  void roundTripThroughParserAndDumper() throws Exception {
    DateTimeFormatter f = DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");
    LocalDateTime begin = LocalDateTime.parse("10/01/2025 1:00 PM", f);
    LocalDateTime end   = LocalDateTime.parse("10/01/2025 2:00 PM", f);

    Appointment a = new Appointment("Demo", begin, end);
    AppointmentBook book = new AppointmentBook("Alice");
    book.addAppointment(a);

    StringWriter out = new StringWriter();
    new TextDumper(out).dump(book);
    String dumped = out.toString();
    assertFalse(dumped.isBlank());

    AppointmentBook parsed = new TextParser(new StringReader(dumped)).parse();
    assertEquals("Alice", parsed.getOwnerName());
    assertEquals(1, parsed.getAppointments().size());
    Appointment p = parsed.getAppointments().iterator().next();
    assertEquals(a.getDescription(), p.getDescription());
    assertEquals(a.getBeginTime(),   p.getBeginTime());
    assertEquals(a.getEndTime(),     p.getEndTime());
  }
}

