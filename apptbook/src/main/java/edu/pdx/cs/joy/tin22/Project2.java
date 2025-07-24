package edu.pdx.cs.joy.tin22;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Project2 {
  private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

  public static void main(String[] args) {
    boolean print = false;
    String textFile = null;
    List<String> positional = new ArrayList<>();
    for (int i = 0; i < args.length; i++) {
      String a = args[i];
      if (a.equals("-README")) {
        printReadme();
        return;
      } else if (a.equals("-print")) {
        print = true;
      } else if (a.equals("-textFile")) {
        if (i + 1 >= args.length) {
          err("Missing filename for -textFile");
        }
        textFile = args[++i];
      } else if (a.startsWith("-")) {
        err("Unknown option: " + a);
      } else {
        positional.add(a);
      }
    }
    if (positional.size() != 6) {
      err("Missing or extraneous arguments");
    }
    String owner = positional.get(0);
    String desc = positional.get(1);
    String beginDT = positional.get(2) + " " + positional.get(3);
    String endDT = positional.get(4) + " " + positional.get(5);
    parseOrError(beginDT, "begin");
    parseOrError(endDT, "end");
    AppointmentBook book;
    if (textFile != null && Files.exists(Path.of(textFile))) {
      try (Reader r = Files.newBufferedReader(Path.of(textFile))) {
        book = new TextParser(r).parse();
        if (!book.getOwnerName().equals(owner)) {
          err("Owner in file (" + book.getOwnerName() + ") does not match owner on command line (" + owner + ")");
        }
      } catch (Exception ex) {
        err("Cannot parse text file: " + ex.getMessage());
        return;
      }
    } else {
      book = new AppointmentBook(owner);
    }
    Appointment appt = new Appointment(desc, beginDT, endDT);
    book.addAppointment(appt);
    if (textFile != null) {
      try {
        Path p = Path.of(textFile);
        Path parent = p.getParent();
        if (parent != null) {
          Files.createDirectories(parent);
        }
        try (Writer w = Files.newBufferedWriter(p)) {
          new TextDumper(w).dump(book);
        }
      } catch (IOException ex) {
        err("Error writing to file: " + ex.getMessage());
      }
    }
    if (print) {
      System.out.println(appt);
    }
  }

  private static void parseOrError(String dt, String which) {
    try {
      LocalDateTime.parse(dt, FORMAT);
    } catch (DateTimeParseException ex) {
      err("Invalid " + which + " time: " + dt);
    }
  }

  private static void err(String msg) {
    System.err.println("Error: " + msg);
    usage();
    System.exit(1);
  }

  private static void usage() {
    System.err.println("usage: java -jar target/apptbook-1.0.0.jar [options] <owner> <description> <begDate> <begTime> <endDate> <endTime>");
    System.err.println("options: -textFile file  -print  -README");
  }

  private static void printReadme() {
    System.out.println("Project 2 Appointment Book – Tin Le");
    System.out.println("Stores appointments in a text file.");
  }
}

