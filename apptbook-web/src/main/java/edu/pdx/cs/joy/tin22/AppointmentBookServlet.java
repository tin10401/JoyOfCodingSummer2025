package edu.pdx.cs.joy.tin22;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AppointmentBookServlet extends HttpServlet {
  private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");
  private final Map<String, AppointmentBook> books = new ConcurrentHashMap<>();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String o = req.getParameter("owner");
    String d = req.getParameter("description");
    String b = req.getParameter("begin");
    String e = req.getParameter("end");
    if (o == null || d == null || b == null || e == null) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "missing parameter");
      return;
    }
    LocalDateTime bt, et;
    try {
      bt = LocalDateTime.parse(b, F);
      et = LocalDateTime.parse(e, F);
    } catch (Exception x) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid date");
      return;
    }
    if (et.isBefore(bt)) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "end before begin");
      return;
    }
    AppointmentBook book = books.computeIfAbsent(o, AppointmentBook::new);
    book.addAppointment(new Appointment(d, bt, et));
    resp.setContentType("text/plain");
    new TextDumper(resp.getWriter()).dump(book);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String o = req.getParameter("owner");
    if (o == null) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "missing owner");
      return;
    }
    AppointmentBook book = books.get(o);
    if (book == null) {
      resp.sendError(HttpServletResponse.SC_NOT_FOUND, "no book");
      return;
    }
    String bp = req.getParameter("begin");
    String ep = req.getParameter("end");
    AppointmentBook out;
    if (bp == null && ep == null) {
      out = book;
    } else if (bp != null && ep != null) {
      LocalDateTime bt, et;
      try {
        bt = LocalDateTime.parse(bp, F);
        et = LocalDateTime.parse(ep, F);
      } catch (Exception x) {
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid date");
        return;
      }
      if (et.isBefore(bt)) {
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "end before begin");
        return;
      }
      out = new AppointmentBook(o);
      for (Appointment a : book.getAppointments())
        if (!a.getBeginTime().isBefore(bt) && !a.getBeginTime().isAfter(et))
          out.addAppointment(a);
    } else {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "begin and end required together");
      return;
    }
    resp.setContentType("text/plain");
    new TextDumper(resp.getWriter()).dump(out);
  }
}

