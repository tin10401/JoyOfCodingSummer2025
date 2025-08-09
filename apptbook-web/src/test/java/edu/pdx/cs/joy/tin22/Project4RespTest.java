package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Project4RespTest {
  @Test
  void coverRecordAccessors() {
    var r = new Project4.Resp(201, "ok");
    assertEquals(201, r.code());
    assertEquals("ok", r.body());
  }
}

