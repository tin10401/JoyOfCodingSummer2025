package edu.pdx.cs.joy.tin22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TextParser {
  private final BufferedReader in;
  private static final DateTimeFormatter FMT =
      DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");

  public TextParser(Reader reader) {
    this.in = new BufferedReader(reader);
  }

  public AppointmentBook parse() {
    try {
      String owner = in.readLine();
      if (owner == null) {
        throw new IOException("Missing owner line");
      }

      AppointmentBook book = new AppointmentBook(owner);

      String line;
      while ((line = in.readLine()) != null) {
        line = line.trim();
        if (line.isEmpty()) {
          continue;
        }

        // Some inputs begin each appointment line with a leading '|'
        if (line.startsWith("|")) {
          line = line.substring(1);
        }

        String[] parts = line.split("\\|");
        if (parts.length != 3) {
          throw new IOException("Bad appointment line: " + line);
        }

        String description = parts[0].trim();
        LocalDateTime begin = LocalDateTime.parse(parts[1].trim(), FMT);
        LocalDateTime end   = LocalDateTime.parse(parts[2].trim(), FMT);

        book.addAppointment(new Appointment(description, begin, end));
      }

      return book;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

