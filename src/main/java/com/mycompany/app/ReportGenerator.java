package com.mycompany.app;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReportGenerator { 
    private Map<String, Student> students = new HashMap<>(); //roll number to student object - Map
    private static final String DATA_FOLDER = "new_2f"; //main folder constant
    private final DataFormatter formatter = new DataFormatter();  //object that formats cell values as strings
    private static final List<String> subjects = new ArrayList<>(Arrays.asList(
        "DD","DBMS","DS","OOPS","EEA","EEC"
    )); //theory subfolders 

    private static final List<String> lab = new ArrayList<>(Arrays.asList(
        "DBMS_LAB","DS_LAB","OOPS_LAB"
    )); //lab subfolders

    public void loadData() throws IOException{
        File folder = new File(DATA_FOLDER); //folder is the object of the main folder
        if(!folder.exists() || !folder.isDirectory()){
            throw new IOException("Data folder not found: " + DATA_FOLDER);
        } 
        /*
        DATA_FOLDER object = folder
        list of subjects = subjects
        subName = subject name in the list of subjects
        */
        for (String subName : subjects) { //for all the subjects under theory group
            File file = new File(folder, subName + ".xlsx"); //file object of each subject
            if (file.exists()) 
                loadTheoryFile(file, subName); //load the theory file
        }

        for (String subName : lab) {
            File file = new File(folder, subName + ".xlsx");
            if (file.exists()) 
                loadLabFile(file, subName);
        }
    }
    private void loadTheoryFile(File file, String subName) throws IOException{
        try (FileInputStream fis = new FileInputStream(file); //opens the file to read bytes from it
            Workbook wb = new XSSFWorkbook(fis)) {
            Sheet sheet = wb.getSheetAt(0); //first sheet
            for(Row row : sheet){
                if(row.getRowNum() < 2) //the first row because it's the header row (skip the header)
                    continue; 
                Cell rollCell = row.getCell(1); //roll number is in the second column whose index is 1
                if (rollCell == null) //skip the empty rows
                    continue;
                String roll = formatter.formatCellValue(rollCell).trim(); //get the roll number as string
                if (roll.isEmpty()) 
                    continue;       
                if(!students.containsKey(roll)){ //if the student with that roll number is not already present in the map
                    Cell nameCell = row.getCell(2); //fetch names from the third columns (index = 2)
                    String name;
                    if(nameCell != null){   //format the name cell value to string
                        name = formatter.formatCellValue(nameCell).trim();
                    } else {
                        name = "Unknown";
                    }   
                    students.put(roll, new Student(roll, name)); //add new student object to the map (key is the roll number, value is the obj)
                }
            
            double mid1, mid2, assignment, presentation;
            if("EEC".equals(subName)){ //EEC pattern is different
                mid1 = getNumericCellValue(row.getCell(2));
                mid2 = getNumericCellValue(row.getCell(3));
                assignment = getNumericCellValue(row.getCell(5));
                presentation = getNumericCellValue(row.getCell(6)); 
            } else { //other subjects 
                mid1 = getNumericCellValue(row.getCell(3));
                mid2 = getNumericCellValue(row.getCell(4));
                assignment = getNumericCellValue(row.getCell(10));  
                presentation = getNumericCellValue(row.getCell(11)); 
            }
            students.get(roll).addTheoryMarks(subName, mid1, mid2, assignment, presentation);
            }        
        } 
    }

    private void loadLabFile(File file, String subName) throws IOException{
        try (FileInputStream fis = new FileInputStream(file);
            Workbook wb = new XSSFWorkbook(fis)) {
            Sheet sheet = wb.getSheetAt(0);
            for (Row row : sheet){
                if(row.getRowNum() < 2)
                    continue; 
                Cell rollCell = row.getCell(1); 
                if (rollCell == null) 
                    continue;
                String roll = formatter.formatCellValue(rollCell).trim();
                if (roll.isEmpty()) 
                    continue;       
                if(!students.containsKey(roll)){
                    Cell nameCell = row.getCell(2);
                    String name;
                    if(nameCell != null){
                        name = formatter.formatCellValue(nameCell).trim();
                    } else {
                        name = "Unknown";
                    }   
                    students.put(roll, new Student(roll, name));
                }
                double int1 = getNumericCellValue(row.getCell(3));
                double int2 = getNumericCellValue(row.getCell(4));
                double avgInt = Math.ceil((int1 + int2) / 2.0);  
                double dayEval = getNumericCellValue(row.getCell(6));
                double viva = getNumericCellValue(row.getCell(7));
                double project = getNumericCellValue(row.getCell(8));
                double total = Math.ceil(avgInt + dayEval + viva + project);  

                students.get(roll).addLabMarks(subName, int1, int2, avgInt, dayEval, viva, project, total);
            }
        }
    }
    public void displayMarks(){
        for (Map.Entry<String, Student> entry : students.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
    public Map<String, Student> getStudents(){
        return this.students;
    }
    private double getNumericCellValue(Cell cell) {
    if (cell == null) return 0.0;
    if (cell.getCellType() == CellType.NUMERIC) {
        return cell.getNumericCellValue();
    } else {
        try {
            String val = formatter.formatCellValue(cell).trim();
            return val.isEmpty() ? 0.0 : Double.parseDouble(val);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    }
}
