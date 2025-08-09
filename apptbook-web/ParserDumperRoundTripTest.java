package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserDumperRoundTripTest {
  @Test
  void roundTrip() throws Exception {
    AppointmentBook orig = new AppointmentBook("Y");
    orig.addAppointment(new Appointment("B",
        LocalDateTime.of(2025, 2, 2, 13, 0),
        LocalDateTime.of(2025, 2, 2, 14, 0)));
    StringWriter sw = new StringWriter();
    new TextDumper(sw).dump(orig);
    AppointmentBook parsed =
        new TextParser(new StringReader(sw.toString())).parse();
    assertEquals(orig.getOwnerName(), parsed.getOwnerName());
    assertEquals(1, parsed.getAppointments().size());
  }
}

