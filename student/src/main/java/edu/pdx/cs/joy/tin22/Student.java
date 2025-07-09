package edu.pdx.cs.joy.tin22;

import edu.pdx.cs.joy.lang.Human;
import java.util.ArrayList;

public class Student extends Human {
    private ArrayList<String> classes;
    private double gpa;
    private String gender;

    public Student(String name, ArrayList<String> classes, double gpa, String gender) {
        super(name);
        this.classes = classes;
        this.gpa = gpa;
        this.gender = gender;
    }

    @Override
    public String says() {
        return "This class is too much work";
    }

    @Override
    public String toString() {
        return getName()
            + " is taking "
            + classes.size()
            + " classes, has a GPA of "
            + gpa
            + ", and identifies as "
            + gender;
    }

    public static void main(String[] args) {
        if (args.length < 4) {
            System.err.println("Missing command line arguments");
            return;
        }
        String name = args[0];
        ArrayList<String> classes = new ArrayList<>();
        if (!args[1].isBlank()) {
            for (String c : args[1].split(",")) {
                classes.add(c);
            }
        }
        double gpa;
        try {
            gpa = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid GPA: " + args[2]);
            return;
        }
        String gender = args[3];
        System.out.println(new Student(name, classes, gpa, gender));
    }
}

