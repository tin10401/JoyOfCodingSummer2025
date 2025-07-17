package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

class Project1IT {

  @Test
  void testNoCommandLineArguments() throws Exception {
    Process p = new ProcessBuilder("java",
        "-jar", "target/apptbook-1.0.0.jar")
        .redirectErrorStream(true)
        .start();

    String output;
    try (BufferedReader br =
           new BufferedReader(new InputStreamReader(p.getInputStream()))) {
      output = br.lines().collect(Collectors.joining("\n"));
    }
    p.waitFor();

    assertThat(output, containsString("usage: java -jar"));
  }
}

