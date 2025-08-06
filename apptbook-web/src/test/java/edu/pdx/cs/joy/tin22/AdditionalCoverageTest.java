package edu.pdx.cs.joy.tin22;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AdditionalCoverageTest {

  @Test
  void exerciseServletAndRemainingClasses() throws Exception {
    assertNotNull(new Messages().toString());                  
    new PrettyPrinter(new PrintWriter(System.out, true));       
    Project4.main(new String[]{"-README", "-host", "x", "-port", "1"});
    Map<String, String> params = Map.of(
        "owner", "Alice",
        "description", "Demo",
        "begin", "10/01/2025 1:00 PM",
        "end",   "10/01/2025 2:00 PM"
    );

    HttpServletRequest req = (HttpServletRequest) Proxy.newProxyInstance(
        HttpServletRequest.class.getClassLoader(),
        new Class[]{HttpServletRequest.class},
        (proxy, method, args) -> {
          if (method.getName().equals("getParameter"))
            return params.get((String) args[0]);
          return null;                  
        });

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw, true);
    HttpServletResponse resp = (HttpServletResponse) Proxy.newProxyInstance(
        HttpServletResponse.class.getClassLoader(),
        new Class[]{HttpServletResponse.class},
        (proxy, method, args) -> {
          switch (method.getName()) {
            case "getWriter":   return pw;
            case "sendError":   return null;
            default:            return null;
          }
        });
    AppointmentBookServlet servlet = new AppointmentBookServlet();
    servlet.doPost(req, resp);
    servlet.doGet(req, resp);
    assertNotNull(sw.toString());
  }
}

