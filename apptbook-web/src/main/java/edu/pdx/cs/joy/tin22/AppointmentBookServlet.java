package edu.pdx.cs.joy.tin22;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class AppointmentBookServlet extends HttpServlet {
  private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");
  private final Map<String, AppointmentBook> books = new HashMap<>();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String o = req.getParameter("owner");
    String d = req.getParameter("description");
    String b = req.getParameter("begin");
    String e = req.getParameter("end");
    if (o == null || d == null || b == null || e == null) { resp.sendError(400, "missing parameter"); return; }
    LocalDateTime bt, et;
    try { bt = LocalDateTime.parse(b, F); et = LocalDateTime.parse(e, F); } catch (Exception x) { resp.sendError(400, "bad date"); return; }
    if (et.isBefore(bt)) { resp.sendError(400, "end before begin"); return; }
    AppointmentBook book = books.computeIfAbsent(o, k -> new AppointmentBook(o));
    book.addAppointment(new Appointment(d, bt, et));
    StringWriter w = new StringWriter();
    new TextDumper(w).dump(book);
    resp.setContentType("text/plain");
    resp.getWriter().print(w.toString());
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String o = req.getParameter("owner");
    if (o == null) { resp.sendError(400, "missing owner"); return; }
    AppointmentBook book = books.get(o);
    if (book == null) { resp.sendError(404, "no book"); return; }
    String bp = req.getParameter("begin");
    String ep = req.getParameter("end");
    AppointmentBook dump;
    if (bp == null && ep == null) {
      dump = book;
    } else if (bp != null && ep != null) {
      LocalDateTime bt, et;
      try { bt = LocalDateTime.parse(bp, F); et = LocalDateTime.parse(ep, F); } catch (Exception x) { resp.sendError(400, "bad date"); return; }
      if (et.isBefore(bt)) { resp.sendError(400, "end before begin"); return; }
      dump = new AppointmentBook(o);
      for (Appointment a : book.getAppointments())
        if (!a.getBeginTime().isBefore(bt) && !a.getBeginTime().isAfter(et))
          dump.addAppointment(a);
    } else { resp.sendError(400, "begin and end together"); return; }
    StringWriter w = new StringWriter();
    new TextDumper(w).dump(dump);
    resp.setContentType("text/plain");
    resp.getWriter().print(w.toString());
  }
}

