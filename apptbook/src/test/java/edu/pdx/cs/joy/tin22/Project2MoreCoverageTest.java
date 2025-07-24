package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

public class Project2MoreCoverageTest {

  @Test
  public void mainCreatesAndUpdatesFile() throws Exception {
    Path f = Files.createTempFile("apptbook", ".txt");
    Files.deleteIfExists(f);
    Project2.main(new String[]{
        "-textFile", f.toString(),
        "-print",
        "Alice", "Lunch",
        "07/24/2025", "12:00",
        "07/24/2025", "13:00"
    });
    assertTrue(Files.exists(f));
    long first = Files.size(f);
    assertTrue(first > 0);
    Project2.main(new String[]{
        "-textFile", f.toString(),
        "Alice", "Coffee",
        "07/24/2025", "15:00",
        "07/24/2025", "15:30"
    });
    long second = Files.size(f);
    assertTrue(second > first);
  }
}

