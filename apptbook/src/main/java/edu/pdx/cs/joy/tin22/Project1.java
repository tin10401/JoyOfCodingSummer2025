package edu.pdx.cs.joy.tin22;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Project1 {
  private static final String USAGE =
    "usage: java -jar target/apptbook-1.0.0.jar [options] <args>\n" +
    "args are: owner description begin-date begin-time end-date end-time\n" +
    "options may appear in any order:\n" +
    "  -print    Prints a description of the new appointment\n" +
    "  -README   Prints a README and exits";
  private static final DateTimeFormatter FMT =
    DateTimeFormatter.ofPattern("M/d/yyyy H:mm");

  public static void main(String[] args) {
    if (args.length == 0) {
      System.err.println(USAGE);
      return;
    }
    boolean print = false;
    List<String> ops = new ArrayList<>();
    for (String arg : args) {
      if ("-print".equals(arg)) {
        print = true;
        continue;
      }
      if ("-README".equals(arg)) {
        System.out.println(
          "Project 1: Appointment Book Application\n" +
          "Author: Tin Le\n" +
          "Creates an appointment book containing one appointment supplied\n" +
          "on the command line. Dates use mm/dd/yyyy hh:mm format.\n\n" +
          USAGE);
        return;
      }
      if (arg.startsWith("-")) {
        error("Unknown option: " + arg);
        return;
      }
      ops.add(arg);
    }
    if (ops.size() < 6) {
      if (ops.size() == 5) error("missing end time"); else error("missing arguments");
      return;
    }
    if (ops.size() > 6) {
      error("extraneous argument: '" + ops.get(6) + "'");
      return;
    }
    String owner = ops.get(0);
    String desc  = ops.get(1);
    String begin = ops.get(2) + " " + ops.get(3);
    String end   = ops.get(4) + " " + ops.get(5);
    if (!parseOrError(begin, "begin time")) return;
    if (!parseOrError(end,   "end time"))   return;
    AppointmentBook book = new AppointmentBook(owner);
    Appointment appt    = new Appointment(desc, begin, end);
    book.addAppointment(appt);
    if (print) System.out.println(appt);
  }

  private static boolean parseOrError(String dt, String which) {
    try {
      LocalDateTime.parse(dt, FMT);
      return true;
    } catch (DateTimeParseException ex) {
      error("invalid " + which + " '" + dt + "'. Expected mm/dd/yyyy hh:mm");
      return false;
    }
  }

  private static void error(String msg) {
    System.err.println("Error: " + msg);
  }
}

