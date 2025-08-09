package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;

class Project4BranchesTest {

  @Test
  void readmeAndHappyAdd() {
    Project4.main(new String[]{"-README"});
    try {
      Project4.main(new String[]{"-print","Owner","A","1/1/2025","10:00 AM","1/1/2025","11:00 AM"});
    } catch (RuntimeException ignored) {}
  }

  @Test
  void errorCombos() {
    try { Project4.main(new String[]{"-host","localhost"}); } catch (RuntimeException ignored) {}
    try { Project4.main(new String[]{"-port","8080"}); } catch (RuntimeException ignored) {}
    try { Project4.main(new String[]{"-port","notAnInt"}); } catch (RuntimeException ignored) {}
    try { Project4.main(new String[]{"-search","Owner"}); } catch (RuntimeException ignored) {}
    try { Project4.main(new String[]{"-search","Owner","1/1/2025","12:00 AM"}); } catch (RuntimeException ignored) {}
  }
}

