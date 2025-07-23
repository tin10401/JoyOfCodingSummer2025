package edu.pdx.cs.joy.tin22;

import edu.pdx.cs.joy.ParserException;
import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

public class Project2 {
  private static final String FMT = "MM/dd/yyyy HH:mm";

  public static void main(String[] args) {
    List<String> a = new ArrayList<>(Arrays.asList(args));
    if (a.contains("-README")) {
      System.out.println("Project 2 Appointment Book – Tin Le\nStores appointments in a text file.");
      return;
    }

    boolean print = a.remove("-print");
    int tf = a.indexOf("-textFile");
    File file = null;
    if (tf != -1) {
      if (tf + 1 >= a.size()) err("missing file path after -textFile");
      file = new File(a.get(tf + 1));
      a.subList(tf, tf + 2).clear();
    }

    if (a.size() != 4) usage("missing or extraneous arguments");

    String owner = a.get(0);
    String desc  = a.get(1);
    String begin = a.get(2);
    String end   = a.get(3);

    parseOrError(begin, "begin");
    parseOrError(end, "end");

    Appointment appt = new Appointment(desc, begin, end);
    AppointmentBook book;

    if (file != null && file.exists()) {
      try (Reader r = new FileReader(file)) {
        book = new TextParser(r).parse();
      } catch (IOException | ParserException e) {
        err("cannot read " + file);
        return;
      }
      if (!book.getOwnerName().equals(owner))
        err("owner mismatch: " + owner + " ≠ " + book.getOwnerName());
    } else {
      book = new AppointmentBook(owner);
    }

    book.addAppointment(appt);
    if (print) System.out.println(appt);

    if (file != null) {
      try {
        if (file.getParentFile() != null)
          Files.createDirectories(file.getParentFile().toPath());
        try (Writer w = new FileWriter(file)) {
          new TextDumper(w).dump(book);
        }
      } catch (IOException e) {
        err("cannot write " + file);
      }
    }
  }

  private static void parseOrError(String dt, String which) {
    try {
      new SimpleDateFormat(FMT).parse(dt);
    } catch (Exception e) {
      err("invalid " + which + " '" + dt + "'. Expected " + FMT);
    }
  }

  private static void usage(String why) {
    System.err.println("Error: " + why);
    System.err.println(
        "usage: java -jar target/apptbook-1.0.0.jar [options] <owner> <description> <begin> <end>");
    System.err.println("options: -textFile file  -print  -README");
    System.exit(1);
  }

  private static void err(String m) {
    System.err.println("Error: " + m);
    System.exit(1);
  }
}

