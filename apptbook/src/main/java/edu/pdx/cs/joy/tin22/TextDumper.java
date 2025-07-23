package edu.pdx.cs.joy.tin22;

import edu.pdx.cs.joy.AppointmentBookDumper;
import java.io.PrintWriter;
import java.io.Writer;

public class TextDumper implements AppointmentBookDumper<AppointmentBook> {
  private final PrintWriter pw;

  public TextDumper(Writer w) {
    this.pw = new PrintWriter(w);
  }

  @Override
  public void dump(AppointmentBook book) {
    pw.println(book.getOwnerName());
    book.getAppointments().forEach(a ->
      pw.printf("%s|%s|%s%n",
        a.getDescription(),
        a.getBeginTimeString(),
        a.getEndTimeString())
    );
    pw.flush();
  }
}

