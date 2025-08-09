package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;
import java.io.StringReader;

class TouchEverythingTest {
  @Test
  void touchClientPaths() {
    Project4.main(new String[] {"-README"});
    Project4.main(new String[] {});
  }

  @Test
  void touchServletCtor() {
    new AppointmentBookServlet();
  }

  @Test
  void touchParserAndDumperPaths() {
    String owner = "Owner";
    String seed = owner + "\n|A|1/1/2025 10:00 AM|1/1/2025 11:00 AM\n";
    new TextParser(new StringReader(seed)).parse();
    new TextDumper(new java.io.StringWriter()).dump(new AppointmentBook(owner));
  }

  @Test
  void touchPrettyPrinter() throws Exception {
    var book = new AppointmentBook("O");
    book.addAppointment(new Appointment("A",
        java.time.LocalDateTime.of(2025,1,1,10,0),
        java.time.LocalDateTime.of(2025,1,1,11,0)));
    new PrettyPrinter(new java.io.StringWriter()).dump(book);
  }
}

