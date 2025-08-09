package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static java.time.Month.AUGUST;
import static org.junit.jupiter.api.Assertions.*;

class FinalCoverageBoosterIT {

  private static Appointment a(String d, int h1, int h2) {
    return new Appointment(d,
        LocalDateTime.of(2025, AUGUST, 6, h1, 0),
        LocalDateTime.of(2025, AUGUST, 6, h2, 0));
  }

  @Test
  void exerciseAbstractAppointmentBook() {
    var list = new ArrayList<Appointment>();
    AbstractAppointmentBook<Appointment> book = new AbstractAppointmentBook<>() {
      @Override public String getOwnerName() { return "Jack"; }
      @Override public Collection<Appointment> getAppointments() { return list; }
      @Override public void addAppointment(Appointment a) { list.add(a); }
    };

    assertEquals("Jack", book.getOwnerName());
    assertTrue(book.getAppointments().isEmpty());

    Appointment first = a("A",10,11);
    book.addAppointment(first);
    book.addAppointment(a("B",12,13));

    assertEquals(2, book.getAppointments().size());
    assertSame(first, book.getAppointments().iterator().next());
  }

  @Test
  void roundTripThroughParserAndDumper() throws Exception {
    AppointmentBook seeded = new AppointmentBook("Jill");
    seeded.addAppointment(a("Call",9,10));

    StringWriter sw = new StringWriter();
    new TextDumper(sw).dump(seeded);

    String in = sw.toString();
    AppointmentBook reparsed =
        new TextParser(new StringReader(in)).parse();

    assertEquals(seeded.getOwnerName(), reparsed.getOwnerName());
    assertEquals(1, reparsed.getAppointments().size());
    assertEquals("Call",
        reparsed.getAppointments().iterator().next().getDescription());
  }
}

