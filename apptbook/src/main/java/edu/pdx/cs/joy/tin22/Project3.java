package edu.pdx.cs.joy.tin22;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class Project3 {
  private static final DateTimeFormatter CLI_FMT = DateTimeFormatter.ofPattern("M/d/yyyy h:mm a", Locale.US);

  public static void main(String[] args) {
    List<String> list = new ArrayList<>(Arrays.asList(args));
    boolean print = false;
    String textFile = null;
    String prettyFile = null;

    for (Iterator<String> it = list.iterator(); it.hasNext();) {
      String s = it.next();
      switch (s) {
        case "-README":
          System.out.println("Project 3: Pretty Printing an Appointment Book");
          return;
        case "-print":
          print = true;
          it.remove();
          break;
        case "-textFile":
          it.remove();
          textFile = needArg(it);
          break;
        case "-pretty":
          it.remove();
          prettyFile = needArg(it);
          break;
      }
    }

    if (list.size() != 8) bail("Missing or extra arguments");

    String owner = list.get(0);
    String description = list.get(1);
    LocalDateTime begin = parse(list.get(2) + " " + list.get(3) + " " + list.get(4));
    LocalDateTime end = parse(list.get(5) + " " + list.get(6) + " " + list.get(7));

    Appointment appt = new Appointment(description, begin, end);
    AppointmentBook book;

    if (textFile != null && new File(textFile).exists()) {
      try (FileReader fr = new FileReader(textFile)) {
        book = new TextParser(fr).parse();
      } catch (Exception e) {
        bail("Cannot read " + textFile);
        return;
      }
      if (!book.getOwnerName().equals(owner)) bail("Owner mismatch in file");
    } else {
      book = new AppointmentBook(owner);
    }

    book.addAppointment(appt);

    if (textFile != null) {
      try (FileWriter fw = new FileWriter(textFile)) {
        new TextDumper(fw).dump(book);
      } catch (Exception e) {
        bail("Cannot write " + textFile);
      }
    }

    if (print) {
      System.out.println(appt.getDescription() + " from " + appt.getBeginTimeString() + " to " + appt.getEndTimeString());
    }

    if (prettyFile != null) {
      try (Writer w = prettyFile.equals("-") ? new OutputStreamWriter(System.out) : new FileWriter(prettyFile)) {
        new PrettyPrinter(w).dump(book);
      } catch (Exception e) {
        bail("Cannot write pretty output");
      }
    }
  }

  private static LocalDateTime parse(String s) {
    try {
      return LocalDateTime.parse(s, CLI_FMT);
    } catch (DateTimeParseException e) {
      bail("Bad date/time: " + s);
      return null;
    }
  }

  private static String needArg(Iterator<String> it) {
    if (!it.hasNext()) bail("Missing argument");
    String v = it.next();
    it.remove();
    return v;
  }

  private static void bail(String msg) {
    System.err.println(msg);
    System.exit(1);
  }
}

