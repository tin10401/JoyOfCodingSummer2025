package edu.pdx.cs.joy.tin22;

import java.io.StringWriter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextDumperTest {
  @Test
  void roundTripDumpAndParse() throws Exception {
    AppointmentBook book = new AppointmentBook("Tin");
    book.addAppointment(new Appointment("chat", "07/30/2025 09:00", "07/30/2025 10:00"));
    StringWriter sw = new StringWriter();
    new TextDumper(sw).dump(book);
    AppointmentBook parsed = new TextParser(new java.io.StringReader(sw.toString())).parse();
    assertEquals(book.getOwnerName(), parsed.getOwnerName());
    assertEquals(1, parsed.getAppointments().size());
  }
}

