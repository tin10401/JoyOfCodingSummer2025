package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;
import java.io.StringReader;
import java.io.StringWriter;
import static org.junit.jupiter.api.Assertions.*;

public class TextParserCoverageTest {

  @Test
  public void dumpThenParseRoundTrip() throws Exception {
    AppointmentBook b = new AppointmentBook("Bob");
    b.addAppointment(new Appointment("Call", "08/01/2025 09:00", "08/01/2025 10:00"));
    StringWriter w = new StringWriter();
    new TextDumper(w).dump(b);
    StringReader r = new StringReader(w.toString());
    AppointmentBook p = new TextParser(r).parse();
    assertEquals("Bob", p.getOwnerName());
    assertEquals(1, p.getAppointments().size());
    assertTrue(p.getAppointments().iterator().next().getDescription().contains("Call"));
  }
}

