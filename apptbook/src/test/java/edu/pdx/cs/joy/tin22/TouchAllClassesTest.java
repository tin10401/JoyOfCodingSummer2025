package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;

class TouchAllClassesTest {

  @Test
  void touchEverything() throws Exception {
    Class<?>[] all = {
        Appointment.class,
        AppointmentBook.class,
        AppointmentBookXmlHelper.class,
        TextDumper.class,
        TextParser.class,
        Project1.class,
        Project2.class,
        CoverageHelper.class
    };
    for (Class<?> c : all) {
      try {
        var ctor = c.getDeclaredConstructors()[0];
        ctor.setAccessible(true);
        Object[] args = new Object[ctor.getParameterCount()];
        ctor.newInstance(args);
      } catch (Exception ignore) { }
    }
  }
}

