package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

class Project1Test {

  private static String invoke(String... args) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream origOut = System.out;
    PrintStream origErr = System.err;
    System.setOut(new PrintStream(baos));
    System.setErr(new PrintStream(baos));
    Project1.main(args);
    System.setOut(origOut);
    System.setErr(origErr);
    return baos.toString();
  }

  @Test
  void noArgsShowsUsage() {
    String out = invoke();
    assertThat(out, containsString("usage: java -jar"));
  }

  @Test
  void readmeOptionPrintsReadme() {
    String out = invoke("-README");
    assertThat(out, containsString("Appointment Book Application"));
  }

  @Test
  void unknownOptionIsReported() {
    String err = invoke("-bogus");
    assertThat(err, containsString("Unknown option"));
  }

  @Test
  void missingEndTimeReported() {
    String err = invoke("Owner","Desc","07/20/2025","12:00","07/20/2025");
    assertThat(err, containsString("missing end time"));
  }

  @Test
  void invalidBeginTimeReported() {
    String err = invoke("O","D","07/20/2025","xx:yy","07/20/2025","13:00");
    assertThat(err, containsString("invalid begin time"));
  }

  @Test
  void happyPathWithPrintShowsAppointment() {
    String out = invoke("-print","O","D","07/20/2025","12:00","07/20/2025","13:00");
    assertThat(out, containsString("D"));
    assertThat(out, containsString("07/20/2025 12:00"));
    assertThat(out, containsString("07/20/2025 13:00"));
  }

  @Test
  void appointmentBookStoresAppointment() {
    AppointmentBook b = new AppointmentBook("Alice");
    Appointment a = new Appointment("Meet","01/01/2026 1:00","01/01/2026 2:00");
    b.addAppointment(a);
    assertEquals(1, b.getAppointments().size());
    assertTrue(b.getAppointments().contains(a));
  }
}

