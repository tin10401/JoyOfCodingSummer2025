package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

class Project1IT {

  @Test
  void testNoCommandLineArguments() {
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    PrintStream origOut = System.out, origErr = System.err;
    System.setOut(new PrintStream(buf));
    System.setErr(new PrintStream(buf));

    // invoke program directly
    Project1.main(new String[0]);

    System.setOut(origOut);
    System.setErr(origErr);

    String output = buf.toString();
    assertThat(output, containsString("usage: java -jar"));
  }
}

