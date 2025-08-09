package edu.pdx.cs.joy.tin22;

import org.junit.jupiter.api.Test;

class Project4IT {

  @Test
  void addAppointmentViaMain() {
    Project4.main(new String[]{
        "-host","localhost","-port","8080",
        "O","C","2/3/2025","8:00 AM","2/3/2025","9:00 AM"
    });
  }

  @Test
  void searchAllViaMain() {
    Project4.main(new String[]{
        "-host","localhost","-port","8080",
        "-search","O"
    });
  }

  @Test
  void searchRangeViaMain() {
    Project4.main(new String[]{
        "-host","localhost","-port","8080",
        "-search","O","1/1/2025","12:00 AM","12/31/2025","11:59 PM"
    });
  }
}

