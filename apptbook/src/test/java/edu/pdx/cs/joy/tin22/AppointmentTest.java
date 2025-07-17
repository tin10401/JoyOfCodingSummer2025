package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {

  @Test
  void fullyPopulatedConstructorStoresValues() {
    Appointment a = new Appointment("Lunch",
        "07/21/2025 12:00",
        "07/21/2025 13:00");

    assertThat(a.getDescription(), is("Lunch"));
    assertThat(a.getBeginTimeString(), is("07/21/2025 12:00"));
    assertThat(a.getEndTimeString(), is("07/21/2025 13:00"));
  }

  @Test
  void defaultConstructorProducesNullFields() {
    Appointment a = new Appointment();
    assertNull(a.getDescription());
    assertNull(a.getBeginTimeString());
    assertNull(a.getEndTimeString());
  }
}

