package edu.pdx.cs.joy.tin22;

import edu.pdx.cs.joy.AbstractAppointment;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Appointment extends AbstractAppointment implements Comparable<Appointment> {
  private static final DateTimeFormatter PARSE_FMT = DateTimeFormatter.ofPattern("M/d/yyyy H:mm", Locale.US);
  private static final DateTimeFormatter PRINT_FMT = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm", Locale.US);

  private String description;
  private LocalDateTime begin;
  private LocalDateTime end;

  public Appointment() {}

  public Appointment(String description, LocalDateTime begin, LocalDateTime end) {
    if (end.isBefore(begin)) throw new IllegalArgumentException();
    this.description = description;
    this.begin = begin;
    this.end = end;
  }

  public Appointment(String description, String beginTime, String endTime) {
    this(description,
         LocalDateTime.parse(beginTime, PARSE_FMT),
         LocalDateTime.parse(endTime,   PARSE_FMT));
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public LocalDateTime getBeginTime() {
    return begin;
  }

  @Override
  public LocalDateTime getEndTime() {
    return end;
  }

  @Override
  public String getBeginTimeString() {
    return begin == null ? null : begin.format(PRINT_FMT);
  }

  @Override
  public String getEndTimeString() {
    return end == null ? null : end.format(PRINT_FMT);
  }

  @Override
  public int compareTo(Appointment o) {
    int c = begin.compareTo(o.begin);
    if (c != 0) return c;
    c = end.compareTo(o.end);
    if (c != 0) return c;
    return description.compareTo(o.description);
  }
}

