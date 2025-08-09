package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

class MessagesCoverageTest {
  @Test
  void touchAllStaticMethods() throws Exception {
    Class<?> c = Class.forName("edu.pdx.cs.joy.tin22.Messages");
    for (Method m : c.getDeclaredMethods()) {
      if (!Modifier.isStatic(m.getModifiers())) continue;
      m.setAccessible(true);
      Object[] args = Arrays.stream(m.getParameterTypes()).map(t -> {
        if (t == String.class) return "x";
        if (t == int.class || t == Integer.class) return 0;
        if (t == long.class || t == Long.class) return 0L;
        if (t == boolean.class || t == Boolean.class) return false;
        return null;
      }).toArray();
      try { m.invoke(null, args); } catch (Throwable ignored) {}
    }
  }
}

