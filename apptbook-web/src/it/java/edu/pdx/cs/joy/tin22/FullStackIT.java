package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FullStackIT {

  private static final int PORT;
  static {
    String p = System.getProperty("it.port");
    PORT = (p == null || p.isBlank()) ? 8080 : Integer.parseInt(p);
  }
  private static final String BASE =
      "http://localhost:" + PORT + "/apptbook";

  /* ─── helpers ──────────────────────────────────────────────── */
  private static String enc(Map<String,String> q) {
    StringBuilder b = new StringBuilder();
    q.forEach((k,v) -> {
      if (b.length() > 0) b.append('&');
      b.append(URLEncoder.encode(k, StandardCharsets.UTF_8))
       .append('=')
       .append(URLEncoder.encode(v, StandardCharsets.UTF_8));
    });
    return b.toString();
  }

  private static String post(Map<String,String> q) throws IOException {
    HttpURLConnection c =
        (HttpURLConnection) new URL(BASE + "/appointments").openConnection();
    c.setRequestMethod("POST");
    c.setDoOutput(true);
    byte[] body = enc(q).getBytes(StandardCharsets.UTF_8);
    c.setFixedLengthStreamingMode(body.length);
    c.setRequestProperty("Content-Type",
                         "application/x-www-form-urlencoded");
    try (OutputStream os = c.getOutputStream()) { os.write(body); }
    try (InputStream is = c.getInputStream()) {
      return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    }
  }

  private static String get(Map<String,String> q) throws IOException {
    URL u = new URL(BASE + "/appointments?" + enc(q));
    try (InputStream is = u.openStream()) {
      return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    }
  }

  /* ─── tests ────────────────────────────────────────────────── */
  @Test @Order(1)
  void addFirstAppt() throws Exception {
    String r = post(Map.of(
        "owner","JUnit",
        "description","Demo appointment",
        "begin","01/01/2025 1:00 PM",
        "end","01/01/2025 2:00 PM"));
    assertTrue(r.contains("Demo appointment"));
  }

  @Test @Order(2)
  void searchHitsAppointment() throws Exception {
    String r = get(Map.of(
        "owner","JUnit",
        "begin","01/01/2025 12:00 PM",
        "end","01/02/2025 12:00 PM"));
    assertTrue(r.contains("Demo appointment"));
  }

  @Test @Order(3)
  void searchMissesAppointment() throws Exception {
    String r = get(Map.of(
        "owner","JUnit",
        "begin","02/01/2025 12:00 PM",
        "end","02/02/2025 12:00 PM"));
    /*  Expect NO appointment lines:  they always contain the description */
    assertFalse(r.contains("Demo appointment"));
  }
}

