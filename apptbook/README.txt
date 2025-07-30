Project 3 – Pretty Printing an Appointment Book
Tin Le  (GitHub tin10401)

This program stores appointments in an appointment book, supports
saving/loading a plain‑text file, and pretty‑prints the book.

----------------------------------------------------------------------
Build
----------------------------------------------------------------------
    mvn clean package

The command above compiles the code, runs the tests, and produces
target/apptbook-1.0.0.jar

----------------------------------------------------------------------
Command‑line
----------------------------------------------------------------------
usage: java -jar target/apptbook-1.0.0.jar [options] <args>
args (in order):
  owner         The person who owns the appt book
  description   A description of the appointment
  begin date    M/d/yyyy       (example 7/30/2025)
  begin time    H:mm           (24‑hour clock, example 14:00)
  end date      M/d/yyyy
  end time      H:mm

options (in any order):
  -textFile file   Read/write the appt book from this text file
  -pretty file     Pretty print the book to a text file or “-” for stdout
  -print           Echo the new appointment to stdout
  -README          Display this README and exit

Example:
  java -jar target/apptbook-1.0.0.jar -print -pretty - \
       "Alice" "Stand‑up" 7/31/2025 9:00 7/31/2025 9:15
