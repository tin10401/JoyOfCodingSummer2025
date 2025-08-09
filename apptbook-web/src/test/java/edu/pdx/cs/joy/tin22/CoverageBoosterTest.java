package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CoverageBoosterTest {

  @Test
  void prettyPrinterHitsEveryLine() throws Exception {
    AppointmentBook book = new AppointmentBook("Jack");
    book.addAppointment(new Appointment(
        "A",
        LocalDateTime.of(2025, 2, 2, 10, 0),
        LocalDateTime.of(2025, 2, 2, 11, 0)));

    StringWriter dumpWriter = new StringWriter();
    new TextDumper(dumpWriter).dump(book);

    AppointmentBook reparsed =
        new TextParser(new StringReader(dumpWriter.toString())).parse();

    assertEquals(book.getOwnerName(), reparsed.getOwnerName());
    assertEquals(1, reparsed.getAppointments().size());

    StringWriter pretty = new StringWriter();
    new PrettyPrinter(pretty).dump(reparsed);

    String out = pretty.toString();
    assertTrue(out.contains("Owner"));
    assertTrue(out.contains("Appointments"));
    assertTrue(out.contains("minutes"));
  }
}

