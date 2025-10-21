package com.mycompany.app;

import java.io.IOException;
import javax.swing.SwingUtilities; 
import javax.swing.JOptionPane;     //error messages (special plane)

//creates the Report and is connected to the GUI 
public class ReportApp {
    public static void main(String[] args) {
        final ReportGenerator generator = new ReportGenerator();

        try {
            //loading Excel data before launching the GUI 
            generator.loadData(); //has two options (theory and lab)
        } catch (IOException e) {
            
            System.err.println("FATAL ERROR: Could not load student data: " + e.getMessage());
            e.printStackTrace();
            
            JOptionPane.showMessageDialog(null, 
                "FATAL ERROR: Could not load student data. \n" +
                "Please check the 'new_2f' data folder and its contents.", 
                "Data Loading Error", 
                JOptionPane.ERROR_MESSAGE);
            return; 
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentReportGUI(generator); 
            }
        });
    }
}
