package edu.pdx.cs.joy.tin22;

import edu.pdx.cs.joy.AppointmentBookParser;
import edu.pdx.cs.joy.ParserException;
import java.io.BufferedReader;
import java.io.Reader;

public class TextParser implements AppointmentBookParser<AppointmentBook> {
  private final BufferedReader br;

  public TextParser(Reader r) {
    this.br = new BufferedReader(r);
  }

  @Override
  public AppointmentBook parse() throws ParserException {
    try {
      String owner = br.readLine();
      if (owner == null || owner.isBlank())
        throw new ParserException("malformed file: missing owner");
      AppointmentBook book = new AppointmentBook(owner.trim());
      String line;
      while ((line = br.readLine()) != null) {
        String[] p = line.split("\\|", 3);
        if (p.length != 3)
          throw new ParserException("malformed file: " + line);
        book.addAppointment(new Appointment(p[0], p[1], p[2]));
      }
      return book;
    } catch (ParserException e) {
      throw e;
    } catch (Exception e) {
      throw new ParserException(e.getMessage(), e);
    }
  }
}

