package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;
import java.io.StringWriter;

class CoverageBooster3Test {

  @Test
  void coverPrettyPrinterAndProject3() {
    AppointmentBook book = new AppointmentBook("dummy");
    book.addAppointment(new Appointment("d", "7/30/2025 10:00", "7/30/2025 11:00"));

    new PrettyPrinter(new StringWriter()).dump(book);

    String[] argv = {
        "-print", "-pretty", "-",
        "Bob", "Meeting",
        "7/31/2025", "9:00", "AM",
        "7/31/2025", "10:00", "AM"
    };
    Project3.main(argv);
  }
}

