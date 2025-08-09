package edu.pdx.cs.joy.tin22;

import java.io.PrintWriter;
import java.io.Writer;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class PrettyPrinter implements AppointmentBookDumper<AppointmentBook> {
  private final PrintWriter out;

  public PrettyPrinter(Writer w) {
    this.out = new PrintWriter(w);
  }

  @Override
  public void dump(AppointmentBook book) {
    DateTimeFormatter fmt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    out.println("Owner: " + book.getOwnerName());
    out.println("Appointments: " + book.getAppointments().size());
    out.println();
    book.getAppointments().forEach(a -> {
      long mins = Duration.between(a.getBeginTime(), a.getEndTime()).toMinutes();
      out.println(a.getDescription());
      out.println("  From: " + a.getBeginTime().format(fmt));
      out.println("    To: " + a.getEndTime().format(fmt));
      out.println("  (" + mins + " minutes)");
      out.println();
    });
    out.flush();
  }
}

