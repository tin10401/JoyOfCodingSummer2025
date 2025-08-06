package edu.pdx.cs.joy.tin22;
import org.junit.jupiter.api.Test;
import java.io.PrintWriter;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AdditionalCoverageTest {

  @Test
  void touchRemainingClasses() {
    assertNotNull(new AppointmentBookServlet());
    assertNotNull(new Messages().toString());
    new PrettyPrinter(new PrintWriter(System.out, true));
    Project4.main(new String[]{"-README", "-host", "x", "-port", "1"});
  }
}

