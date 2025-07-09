package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

class StudentAdditionalTest {

  @Test
  void saysReturnsCorrectString() {
    var s = new Student("X", new ArrayList<>(), 0.0, "other");
    assertThat(s.says(), equalTo("This class is too much work"));
  }

  @Test
  void toStringFormatsNameClassesGpaGender() {
    var classes = new ArrayList<String>();
    classes.add("Math");
    classes.add("CS");
    var s = new Student("A", classes, 3.2, "female");
    assertThat(
      s.toString(),
      equalTo("A is taking 2 classes, has a GPA of 3.2, and identifies as female")
    );
  }

  @Test
  void mainWithArgsPrintsStudentInfo() {
    String[] args = { "Bob", "Eng,Hist", "4.0", "male" };
    var baos = new java.io.ByteArrayOutputStream();
    System.setOut(new java.io.PrintStream(baos));
    Student.main(args);
    assertThat(
      baos.toString().trim(),
      equalTo("Bob is taking 2 classes, has a GPA of 4.0, and identifies as male")
    );
  }
}

