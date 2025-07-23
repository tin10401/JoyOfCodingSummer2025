package edu.pdx.cs.joy.tin22;

import edu.pdx.cs.joy.ParserException;
import java.io.StringReader;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextParserTest {
  @Test
  void parsesValidContent() throws ParserException {
    String data = "Tin\nmeet|07/30/2025 09:00|07/30/2025 10:00\n";
    AppointmentBook book = new TextParser(new StringReader(data)).parse();
    assertEquals("Tin", book.getOwnerName());
    assertEquals(1, book.getAppointments().size());
  }

  @Test
  void throwsOnMissingOwner() {
    assertThrows(ParserException.class,
      () -> new TextParser(new StringReader("")).parse());
  }
}

