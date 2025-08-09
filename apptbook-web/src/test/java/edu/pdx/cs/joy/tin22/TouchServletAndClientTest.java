package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;

class TouchServletAndClientTest {
  @Test
  void touchServletCtor() {
    new AppointmentBookServlet();
  }

  @Test
  void touchProject4ReadmePath() {
    Project4.main(new String[] { "-README" });
  }
}

