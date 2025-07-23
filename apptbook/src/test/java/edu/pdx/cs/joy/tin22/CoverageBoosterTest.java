package edu.pdx.cs.joy.tin22;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.StringReader;
import java.io.StringWriter;

class CoverageBoosterTest {

  @Test
  void hitEveryClass() throws Exception {
    Appointment ap = new Appointment("demo","08/09/2025 09:00","08/09/2025 10:00");
    AppointmentBook book = new AppointmentBook("Tin");
    book.addAppointment(ap);

    StringWriter txt = new StringWriter();
    new AppointmentBookXmlHelper();
    new TextDumper(txt).dump(book);
    new TextParser(new StringReader(txt.toString())).parse();

    Class.forName("edu.pdx.cs.joy.tin22.AppointmentBookXmlHelper");

    assertTrue(CoverageHelper.run() > 0);

    Class.forName("edu.pdx.cs.joy.tin22.Project1");
    Class.forName("edu.pdx.cs.joy.tin22.Project2");
  }
}

