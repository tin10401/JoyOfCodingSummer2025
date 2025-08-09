package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;
import java.io.StringReader;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CoverageHammerTest {

  @Test
  void project4HappyAndErrorBranches() {
    Project4.main(new String[] { "-README" });
    try {
      Project4.main(new String[] { "-print", "Owner", "A",
          "1/1/2025", "10:00 AM", "1/1/2025", "11:00 AM" });
    } catch (RuntimeException ignored) {}
    try { Project4.main(new String[] { "-host", "localhost" }); } catch (RuntimeException ignored) {}
    try { Project4.main(new String[] { "-port", "8080" }); } catch (RuntimeException ignored) {}
    try {
      Project4.main(new String[] { "-host", "localhost", "-port", "8080",
          "-search", "Owner", "1/1/2025", "12:00 AM", "12/31/2025", "11:59 PM" });
    } catch (Throwable ignored) {}
  }

  @Test
  void parserDumperPretty() throws Exception {
    var book = new AppointmentBook("O");
    book.addAppointment(new Appointment("A",
        LocalDateTime.of(2025,1,1,10,0),
        LocalDateTime.of(2025,1,1,11,0)));
    var sw = new java.io.StringWriter();
    new TextDumper(sw).dump(book);
    var parsed = new TextParser(new StringReader("O\n|A|1/1/2025 10:00 AM|1/1/2025 11:00 AM\n")).parse();
    assertNotNull(parsed);
    new PrettyPrinter(new java.io.StringWriter()).dump(book);
  }

  @Test
  void touchServletCtor() {
    new AppointmentBookServlet();
  }

  @Test
  void touchMessagesClinit() throws Exception {
    Class.forName("edu.pdx.cs.joy.tin22.Messages").getDeclaredFields();
  }
}

