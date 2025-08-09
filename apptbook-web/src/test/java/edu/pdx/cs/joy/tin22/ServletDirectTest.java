package edu.pdx.cs.joy.tin22;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

class ServletDirectTest {

  private static HttpServletRequest req(Map<String, String> p, String method) {
    Map<String, String[]> pm = new HashMap<>();
    p.forEach((k, v) -> pm.put(k, new String[]{v}));
    return (HttpServletRequest) Proxy.newProxyInstance(
        HttpServletRequest.class.getClassLoader(),
        new Class[]{HttpServletRequest.class},
        (proxy, m, args) -> {
          String name = m.getName();
          if (name.equals("getParameter")) return p.get((String) args[0]);
          if (name.equals("getParameterMap")) return pm;
          if (name.equals("getMethod")) return method;
          if (name.equals("getCharacterEncoding")) return "UTF-8";
          Class<?> rt = m.getReturnType();
          if (rt == int.class) return 0;
          if (rt == long.class) return 0L;
          if (rt == boolean.class) return false;
          return null;
        });
  }

  private static class Resp {
    final StringWriter out = new StringWriter();
    int status = 200;
    String contentType = null;

    HttpServletResponse proxy() {
      return (HttpServletResponse) Proxy.newProxyInstance(
          HttpServletResponse.class.getClassLoader(),
          new Class[]{HttpServletResponse.class},
          (proxy, m, args) -> {
            String name = m.getName();
            if (name.equals("getWriter")) return new PrintWriter(out, true);
            if (name.equals("setStatus")) { status = (Integer) args[0]; return null; }
            if (name.equals("setContentType")) { contentType = (String) args[0]; return null; }
            Class<?> rt = m.getReturnType();
            if (rt == int.class) return 0;
            if (rt == long.class) return 0L;
            if (rt == boolean.class) return false;
            return null;
          });
    }
  }

  @Test
  void post_get_all_search_and_errors() throws Exception {
    AppointmentBookServlet s = new AppointmentBookServlet();

    Map<String, String> post1 = new HashMap<>();
    post1.put("owner", "O");
    post1.put("description", "A");
    post1.put("begin", "1/1/2025 10:00 AM");
    post1.put("end", "1/1/2025 11:00 AM");
    s.doPost(req(post1, "POST"), new Resp().proxy());

    Map<String, String> post2 = new HashMap<>();
    post2.put("owner", "O");
    post2.put("description", "B");
    post2.put("begin", "2/1/2025 9:00 AM");
    post2.put("end", "2/1/2025 10:00 AM");
    s.doPost(req(post2, "POST"), new Resp().proxy());

    Map<String, String> getAll = new HashMap<>();
    getAll.put("owner", "O");
    s.doGet(req(getAll, "GET"), new Resp().proxy());

    Map<String, String> search = new HashMap<>();
    search.put("owner", "O");
    search.put("begin", "1/1/2025 12:00 AM");
    search.put("end", "12/31/2025 11:59 PM");
    s.doGet(req(search, "GET"), new Resp().proxy());

    Map<String, String> badGet = new HashMap<>();
    badGet.put("begin", "bad");
    badGet.put("end", "also bad");
    s.doGet(req(badGet, "GET"), new Resp().proxy());

    Map<String, String> badPost1 = new HashMap<>();
    badPost1.put("owner", "O");
    badPost1.put("description", "C");
    badPost1.put("begin", "bad");
    badPost1.put("end", "1/1/2025 11:00 AM");
    try { s.doPost(req(badPost1, "POST"), new Resp().proxy()); } catch (ServletException ignored) {}

    Map<String, String> badPost2 = new HashMap<>();
    badPost2.put("owner", "O");
    badPost2.put("description", "D");
    badPost2.put("begin", "1/1/2025 10:00 AM");
    badPost2.put("end", "bad");
    try { s.doPost(req(badPost2, "POST"), new Resp().proxy()); } catch (ServletException ignored) {}

    Map<String, String> missing = new HashMap<>();
    missing.put("owner", "O");
    try { s.doPost(req(missing, "POST"), new Resp().proxy()); } catch (ServletException ignored) {}
  }
}

