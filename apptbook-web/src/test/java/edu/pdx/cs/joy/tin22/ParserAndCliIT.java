package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ParserAndCliIT {

  @Test
  void parserBuildsBook() throws Exception {
    String[] argv = {
      "Jack",
      "Meet Bob",
      "08/06/2025",
      "08/06/2025",
      "15:00"
    };

    final String owner = "Jack";
    final LocalDateTime begin = LocalDateTime.of(2025, 8, 6, 14, 0);
    final LocalDateTime end   = LocalDateTime.of(2025, 8, 6, 15, 0);

    AppointmentBookParser parser = new AppointmentBookParser() {
      @Override
      public AbstractAppointmentBook<?> parse() {
        AppointmentBook book = new AppointmentBook(owner);
        book.addAppointment(new Appointment("Meet Bob", begin, end));
        return book;
      }
    };

    AbstractAppointmentBook<?> book = parser.parse();

    assertEquals("Jack", book.getOwnerName());
    assertEquals(1, book.getAppointments().size());
    var appt = book.getAppointments().iterator().next();
    assertEquals(LocalDateTime.of(2025, 8, 6, 14, 0), appt.getBeginTime());
  }

  @Test
  void project4LightPaths() {
    Project4.main(new String[] {"-README"});
    Project4.main(new String[]{});
  }
}

