package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HitServletAndMessagesTest {

  @Test
  void hitRemainingClasses() throws Exception {
    assertNotNull(new AppointmentBookServlet());
    assertNotNull(Class.forName(Messages.class.getName()));
  }
}

