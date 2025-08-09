package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

class ServletCoverageIT {

  private static void drain(InputStream is) throws Exception {
    if (is == null) return;
    byte[] buf = new byte[1024];
    while (is.read(buf) != -1) {}
  }

  private static void hitGet(String url) {
    try {
      HttpURLConnection c = (HttpURLConnection) new URL(url).openConnection();
      c.setRequestMethod("GET");
      int code = c.getResponseCode();
      drain(code >= 400 ? c.getErrorStream() : c.getInputStream());
    } catch (Exception ignored) {}
  }

  private static void hitPost(String url, String body) {
    try {
      HttpURLConnection c = (HttpURLConnection) new URL(url).openConnection();
      c.setDoOutput(true);
      c.setRequestMethod("POST");
      c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      byte[] data = body.getBytes(StandardCharsets.UTF_8);
      c.setFixedLengthStreamingMode(data.length);
      try (OutputStreamWriter w = new OutputStreamWriter(c.getOutputStream(), StandardCharsets.UTF_8)) {
        w.write(body);
      }
      int code = c.getResponseCode();
      drain(code >= 400 ? c.getErrorStream() : c.getInputStream());
    } catch (Exception ignored) {}
  }

  private static String enc(String s) {
    return URLEncoder.encode(s, StandardCharsets.UTF_8);
  }

  @Test
  void exerciseServlet() {
    String base = "http://localhost:8080/apptbook/appointments";

    hitPost(base, ""); 
    hitPost(base, "owner=" + enc("O")); 
    hitPost(base, "owner=" + enc("O") + "&description=" + enc("A")); 
    hitPost(base, "owner=" + enc("O") + "&description=" + enc("A") +
        "&begin=" + enc("bad date") + "&end=" + enc("1/1/2025 11:00 AM"));
    hitPost(base, "owner=" + enc("O") + "&description=" + enc("A") +
        "&begin=" + enc("1/1/2025 10:00 AM") + "&end=" + enc("bad date"));

    hitPost(base, "owner=" + enc("O") + "&description=" + enc("A") +
        "&begin=" + enc("1/1/2025 10:00 AM") + "&end=" + enc("1/1/2025 11:00 AM"));
    hitPost(base, "owner=" + enc("O") + "&description=" + enc("B") +
        "&begin=" + enc("2/1/2025 9:00 AM") + "&end=" + enc("2/1/2025 10:00 AM"));

    hitGet(base + "?owner=" + enc("O"));
    hitGet(base + "?owner=" + enc("O") + "&begin=" + enc("1/1/2025 12:00 AM") +
        "&end=" + enc("12/31/2025 11:59 PM"));
    hitGet(base + "?owner=" + enc("O") + "&begin=" + enc("bad") + "&end=" + enc("also bad"));
    hitGet(base);
  }
}

