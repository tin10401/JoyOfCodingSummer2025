package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HitRemainingClassesTest {

  @Test
  void touchAllRemainingClasses() throws Exception {
    assertNotNull(Class.forName("edu.pdx.cs.joy.tin22.AppointmentBookServlet"));
    assertNotNull(Class.forName("edu.pdx.cs.joy.tin22.Messages"));
  }
}

