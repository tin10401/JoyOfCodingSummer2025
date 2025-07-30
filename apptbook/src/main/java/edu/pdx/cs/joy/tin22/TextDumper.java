package edu.pdx.cs.joy.tin22;

import edu.pdx.cs.joy.AppointmentBookDumper;
import java.io.PrintWriter;
import java.io.Writer;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TextDumper implements AppointmentBookDumper<AppointmentBook> {
  private static final DateTimeFormatter PRINT_FMT =
      DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm", Locale.US);

  private final PrintWriter out;

  public TextDumper(Writer w) {
    this.out = new PrintWriter(w);
  }

  @Override
  public void dump(AppointmentBook book) {
    out.println(book.getOwnerName());
    book.getAppointments().forEach(a ->
        out.printf("%s|%s|%s%n",
                   a.getDescription(),
                   a.getBeginTime().format(PRINT_FMT),
                   a.getEndTime().format(PRINT_FMT)));
    out.flush();
  }
}

