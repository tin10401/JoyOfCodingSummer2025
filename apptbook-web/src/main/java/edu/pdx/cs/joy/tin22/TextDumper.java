package edu.pdx.cs.joy.tin22;

import java.io.PrintWriter;
import java.io.Writer;
import java.time.format.DateTimeFormatter;

public class TextDumper implements AppointmentBookDumper<AppointmentBook> {
  private final PrintWriter out;
  private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");

  public TextDumper(Writer w) {
    this.out = new PrintWriter(w);
  }

  @Override
  public void dump(AppointmentBook book) {
    out.println(book.getOwnerName());
    book.getAppointments().forEach(a ->
        out.println(a.getDescription() + "|" +
                    a.getBeginTime().format(FMT) + "|" +
                    a.getEndTime().format(FMT)));
    out.flush();
  }
}

