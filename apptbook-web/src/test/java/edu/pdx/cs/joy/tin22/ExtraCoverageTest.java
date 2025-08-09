package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ExtraCoverageTest {

  @Test
  void abstractBookBasics() {
    AbstractAppointmentBook<Appointment> book = new AbstractAppointmentBook<>() {
      private final AppointmentBook inner = new AppointmentBook("Unit");
      @Override public String getOwnerName() { return inner.getOwnerName(); }
      @Override public Collection<Appointment> getAppointments() { return inner.getAppointments(); }
      @Override public void addAppointment(Appointment a) { inner.addAppointment(a); }
    };

    assertEquals("Unit", book.getOwnerName());

    Appointment a = new Appointment(
        "meet",
        LocalDateTime.of(2025, 1, 1, 9, 0),
        LocalDateTime.of(2025, 1, 1, 10, 0)
    );
    book.addAppointment(a);
    assertTrue(book.getAppointments().contains(a));
  }

  @Test
  void dumperHitsAllBranches() throws Exception {
    AppointmentBook original = new AppointmentBook("Jack");
    original.addAppointment(new Appointment(
        "A",
        LocalDateTime.of(2025, 2, 2, 10, 0),
        LocalDateTime.of(2025, 2, 2, 11, 0)
    ));

    StringWriter sw = new StringWriter();
    new TextDumper(sw).dump(original);

    AppointmentBook reparsed =
        new TextParser(new StringReader(sw.toString())).parse();

    assertEquals(original.getOwnerName(), reparsed.getOwnerName());
    assertEquals(1, reparsed.getAppointments().size());
    assertEquals(
        original.getAppointments().iterator().next().getDescription(),
        reparsed.getAppointments().iterator().next().getDescription()
    );
  }
}

