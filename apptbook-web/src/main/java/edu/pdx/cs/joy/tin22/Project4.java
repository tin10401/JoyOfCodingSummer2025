package edu.pdx.cs.joy.tin22;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Project4 {
  private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");
  public static void main(String[] a) {
    try { run(a); } catch (Exception x) { System.err.println(x.getMessage()); System.exit(1); }
  }
  private static void run(String[] a) throws Exception {
    boolean search = false, print = false; String host = null; int port = 0; List<String> rest = new ArrayList<>();
    for (int i = 0; i < a.length; i++) {
      switch (a[i]) {
        case "-host": host = a[++i]; break;
        case "-port": port = Integer.parseInt(a[++i]); break;
        case "-search": search = true; break;
        case "-print": print = true; break;
        case "-README": System.out.println("Appointment-book REST client"); return;
        default: rest.add(a[i]);
      }
    }
    if ((host == null) != (port == 0)) throw new IllegalArgumentException("host and port together");
    if (host == null) throw new IllegalArgumentException("missing host");
    String base = "http://" + host + ':' + port + "/apptbook/appointments";
    if (search) { doSearch(base, rest); return; }
    doAdd(print, base, rest);
  }
  private static void doSearch(String base, List<String> r) throws Exception {
    if (r.size() != 1 && r.size() != 5) throw new IllegalArgumentException();
    String url = base + "?owner=" + enc(r.get(0));
    if (r.size() == 5) url += "&begin=" + enc(r.get(1)+' '+r.get(2)) + "&end=" + enc(r.get(3)+' '+r.get(4));
    Resp resp = get(url); if (resp.code != 200) throw new RuntimeException(resp.body);
    System.out.println(resp.body.trim());
  }
  private static void doAdd(boolean print, String base, List<String> r) throws Exception {
    if (r.size() != 6) throw new IllegalArgumentException();
    String owner = r.get(0), desc = r.get(1), begin = r.get(2)+' '+r.get(3), end = r.get(4)+' '+r.get(5);
    LocalDateTime.parse(begin, F); LocalDateTime.parse(end, F);
    String body = "owner=" + enc(owner) + "&description=" + enc(desc) + "&begin=" + enc(begin) + "&end=" + enc(end);
    Resp resp = post(base, body); if (resp.code != 200) throw new RuntimeException(resp.body);
    if (print) System.out.println(resp.body.trim());
  }
  private static Resp get(String u) throws Exception {
    HttpURLConnection c = (HttpURLConnection) new URL(u).openConnection(); c.setRequestMethod("GET"); return read(c);
  }
  private static Resp post(String u, String b) throws Exception {
    HttpURLConnection c = (HttpURLConnection) new URL(u).openConnection(); c.setRequestMethod("POST"); c.setDoOutput(true);
    c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    try (BufferedWriter w = new BufferedWriter(new OutputStreamWriter(c.getOutputStream(), StandardCharsets.UTF_8))) { w.write(b); }
    return read(c);
  }
  private static Resp read(HttpURLConnection c) throws Exception {
    int code = c.getResponseCode();
    BufferedReader r = new BufferedReader(new InputStreamReader(code < 400 ? c.getInputStream() : c.getErrorStream(), StandardCharsets.UTF_8));
    StringBuilder sb = new StringBuilder(); String line; while ((line = r.readLine()) != null) sb.append(line).append('\n'); r.close();
    return new Resp(code, sb.toString());
  }
  private static String enc(String s) throws Exception { return URLEncoder.encode(s, StandardCharsets.UTF_8.name()); }
  private record Resp(int code, String body) { }
}

