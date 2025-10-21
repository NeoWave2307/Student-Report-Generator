package com.mycompany.app; 
import java.util.*;

public class Student {
    private String rollNumber; 
    private String name;

    private Map<String, List<Double>> theoryMarks = new HashMap<>(); //Map 2
    /*
    key is the sub name, values are the list of marks
    so when addTheoryMarks is called, it adds the marks into the specific subject 
     */

    public Student(String rollNumber, String name) {
        this.rollNumber = rollNumber;
        this.name = name;
    }
    
    public String getRollNumber() {
        return rollNumber;  
    }

    public String getName(){
        return name;
    }

    public void addTheoryMarks(String subject, double mid1, double mid2, double assignment, double presentation) {
        theoryMarks.put(subject, Arrays.asList(mid1, mid2, assignment, presentation)); //calls the Map
    }

    public Map<String, List<Double>> getTheoryMarks() {
        return theoryMarks;  
    }

    private Map<String, List<Double>> labMarks = new HashMap<>(); //Map 3

    public void addLabMarks(String subject, double int1, double int2, double avgInt,
                            double dayEval, double viva, double project, double total) {
        labMarks.put(subject, Arrays.asList(int1, int2, avgInt, dayEval, viva, project, total));
    }

    public Map<String, List<Double>> getLabMarks() {
        return labMarks;
    }

    public void Displayinfo(){
        System.out.println("Roll Number: " + rollNumber +"\nName: " + name);
    }
    @Override
    public String toString() {
        return "Student{" +
                "rollNumber='" + rollNumber + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
} 