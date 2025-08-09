package edu.pdx.cs.joy.tin22;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Project4 {

  private static final DateTimeFormatter DT = DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");

  public static void main(String[] args) {
    if (args.length == 0) {
      printUsage();
      return;
    }

    boolean readme  = false;
    boolean print   = false;
    boolean search  = false;
    String  host    = null;
    int     port    = -1;
    List<String> r  = new ArrayList<>();

    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "-README" -> readme = true;
        case "-print"  -> print  = true;
        case "-search" -> search = true;
        case "-host"   -> {
          if (++i >= args.length) die("missing host");       host = args[i];
        }
        case "-port"   -> {
          if (++i >= args.length) die("missing port");       try { port = Integer.parseInt(args[i]); }
                                                             catch (NumberFormatException e) { die("port must be an integer"); }
        }
        default        -> r.add(args[i]);
      }
    }

    if (readme) {
      System.out.println("Tin Le – Project 4: Appointment-Book client\n" +
                         "Adds, searches, and pretty-prints appointments via the REST server.");
      printUsage();
      return;
    }

    if (host == null || port < 0) die("host and port must be specified");
    String base = "http://" + host + ':' + port + "/apptbook/appointments";

    try {
      if (search) {
        if (r.size() != 5) die("for -search: owner beginDate beginTime endDate endTime");
        doSearch(base, r);
      } else {
        if (r.size() != 6) die("owner desc beginDate beginTime endDate endTime");
        doAdd(print, base, r);
      }
    } catch (Exception ex) {
      System.err.println("** " + ex.getMessage());
    }
  }

  private static void doSearch(String base, List<String> a) throws Exception {
    String u = base + "?owner=" + enc(a.get(0)) +
        "&begin=" + enc(a.get(1) + ' ' + a.get(2)) +
        "&end="   + enc(a.get(3) + ' ' + a.get(4));
    System.out.println(get(u).body());
  }

  private static void doAdd(boolean p, String base, List<String> a) throws Exception {
    String body = "owner=" + enc(a.get(0)) +
        "&description=" + enc(a.get(1)) +
        "&begin=" + enc(a.get(2) + ' ' + a.get(3)) +
        "&end="   + enc(a.get(4) + ' ' + a.get(5));
    Resp r = post(base, body);
    if (p && r.code() == 200) {
      Appointment ap = new Appointment(a.get(1),
          LocalDateTime.parse(a.get(2) + ' ' + a.get(3), DT),
          LocalDateTime.parse(a.get(4) + ' ' + a.get(5), DT));
      System.out.println(ap);
    } else {
      System.out.println(r.body());
    }
  }

  private static Resp get(String u) throws Exception {
    HttpURLConnection c = (HttpURLConnection) new URL(u).openConnection();
    c.setRequestMethod("GET");
    return read(c);
  }

  private static Resp post(String u, String b) throws Exception {
    byte[] data = b.getBytes(StandardCharsets.UTF_8);
    HttpURLConnection c = (HttpURLConnection) new URL(u).openConnection();
    c.setRequestMethod("POST");
    c.setDoOutput(true);
    c.setFixedLengthStreamingMode(data.length);
    c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    try (OutputStreamWriter w = new OutputStreamWriter(c.getOutputStream(), StandardCharsets.UTF_8)) {
      w.write(b);
    }
    return read(c);
  }

  private static Resp read(HttpURLConnection c) throws Exception {
    int code = c.getResponseCode();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(
        code >= 400 ? c.getErrorStream() : c.getInputStream(), StandardCharsets.UTF_8))) {
      StringBuilder sb = new StringBuilder();
      br.lines().forEach(l -> sb.append(l).append('\n'));
      return new Resp(code, sb.toString().trim());
    }
  }

  private static String enc(String s) {
    return URLEncoder.encode(s, StandardCharsets.UTF_8);
  }

  private static void die(String m) {
      System.err.println(m);
      printUsage();
      throw new RuntimeException(m);
  }

  private static void printUsage() {
    System.out.println("""
        usage: Project4 [-host <hostname> -port <port>] [-search] [-print] \\
               <owner> <description> <begin date> <begin time> <end date> <end time>

          -README   Prints a project description
          -print    Prints the appointment that was just added
          -search   Searches appointments in the given date/time range
          -host     Hostname where the REST server is running
          -port     Port on which the REST server is listening
        """);
  }

  record Resp(int code, String body) { }
}

