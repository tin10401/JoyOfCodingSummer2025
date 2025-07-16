package edu.pdx.cs.joy.tin22;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Project1 {
  private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("M/d/yyyy H:mm");

  public static void main(String[] args) {
    if (args.length == 0) {
      System.err.println("Missing command line arguments");
      return;
    }
    boolean printFlag = false;
    boolean readmeFlag = false;
    int idx = 0;
    while (idx < args.length && args[idx].startsWith("-")) {
      switch (args[idx++]) {
        case "-print":  printFlag  = true; break;
        case "-README": readmeFlag = true; break;
        default: error("Unknown option: " + args[idx-1]);
      }
    }
    if (readmeFlag) {
      System.out.println(
        "Project 1: Appointment Book CLI\n" +
        "Usage: java -jar target/apptbook-1.0.0.jar [options] <args>\n" +
        "options: -print, -README\n" +
        "args: owner, description, begin-date, begin-time, end-date, end-time"
      );
      return;
    }
    String[] tok = new String[args.length - idx];
    System.arraycopy(args, idx, tok, 0, tok.length);
    if (tok.length < 6) error("Error: missing arguments");
    if (tok.length > 6) error("Error: too many arguments");
    String owner = tok[0];
    String desc  = tok[1];
    String bDate = tok[2];
    String bTime = tok[3];
    String eDate = tok[4];
    String eTime = tok[5];
    if (desc.trim().isEmpty()) error("Error: description cannot be empty");
    parseOrError(bDate + " " + bTime, "begin");
    parseOrError(eDate + " " + eTime, "end");
    AppointmentBook book = new AppointmentBook(owner);
    Appointment appt = new Appointment(desc, bDate + " " + bTime, eDate + " " + eTime);
    book.addAppointment(appt);
    if (printFlag) System.out.println(appt);
  }

  private static void parseOrError(String dt, String which) {
    try {
      LocalDateTime.parse(dt, FMT);
    } catch (DateTimeParseException e) {
      error("Error: invalid " + which + " time '" + dt + "'. Expected mm/dd/yyyy hh:mm");
    }
  }

  private static void error(String msg) {
    System.err.println(msg);
    System.exit(1);
  }
}

