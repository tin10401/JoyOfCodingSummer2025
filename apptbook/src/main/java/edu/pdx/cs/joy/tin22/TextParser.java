package edu.pdx.cs.joy.tin22;

import edu.pdx.cs.joy.AppointmentBookParser;
import edu.pdx.cs.joy.ParserException;
import java.io.BufferedReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TextParser implements AppointmentBookParser<AppointmentBook> {
  private static final DateTimeFormatter PARSE_FMT = DateTimeFormatter.ofPattern("M/d/yyyy H:mm", Locale.US);
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
        if (line.isBlank()) continue;
        String[] p = line.split("\\|", 3);
        if (p.length != 3)
          throw new ParserException("malformed file: " + line);
        LocalDateTime begin = LocalDateTime.parse(p[1], PARSE_FMT);
        LocalDateTime end   = LocalDateTime.parse(p[2], PARSE_FMT);
        book.addAppointment(new Appointment(p[0], begin, end));
      }
      return book;
    } catch (ParserException e) {
      throw e;
    } catch (Exception e) {
      throw new ParserException(e.getMessage(), e);
    }
  }
}

