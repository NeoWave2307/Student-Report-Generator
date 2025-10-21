   package com.mycompany.app;

   
   import javax.swing.*;
   import javax.swing.table.DefaultTableModel;
   import java.awt.*;
   import java.util.List;
   import java.util.Map;

   public class StudentReportGUI extends JFrame {
       private ReportGenerator generator;
       private JTextField rollInput;
       private JLabel rollLabel, nameLabel;
       private JTable theoryTable, labTable;
       private DefaultTableModel theoryModel, labModel;

       public StudentReportGUI(ReportGenerator generator) {
           this.generator = generator;
           initializeGUI();
    }

    private void initializeGUI() {
    setTitle("Student Report Generator");
    setSize(1000, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());


    Color bgColor = new Color(18, 18, 18);   // deep dark grey
    Color panelColor = new Color(28, 28, 28); // slightly lighter for panels
    Color textColor = new Color(220, 220, 220);
    Color accentColor = new Color(100, 149, 237); // Cornflower blue

    
    // Global font
    Font font = new Font("Segoe UI", Font.PLAIN, 14);

    // Input Panel
    JPanel inputPanel = new JPanel();
    inputPanel.setBackground(panelColor);
    inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JLabel rollPrompt = new JLabel("Enter Roll Number (in caps):");
    rollPrompt.setForeground(textColor);
    rollPrompt.setFont(font);
    inputPanel.add(rollPrompt);

    rollInput = new JTextField(15);
    rollInput.setFont(font);
    rollInput.setBackground(bgColor);
    rollInput.setForeground(textColor);
    rollInput.setCaretColor(accentColor);
    inputPanel.add(rollInput);

    JButton generateButton = new JButton("Generate Report");
    generateButton.setBackground(accentColor);
    generateButton.setForeground(Color.WHITE);
    generateButton.setFocusPainted(false);
    generateButton.setFont(font);
    inputPanel.add(generateButton);

    add(inputPanel, BorderLayout.NORTH);

    // Output Panel
    JPanel outputPanel = new JPanel();
    outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));
    outputPanel.setBackground(bgColor);

    rollLabel = new JLabel("Roll Number: ");
    nameLabel = new JLabel("Name: ");
    for (JLabel lbl : new JLabel[]{rollLabel, nameLabel}) {
        lbl.setForeground(textColor);
        lbl.setFont(font.deriveFont(Font.BOLD, 15));
        lbl.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 0));
        outputPanel.add(lbl);
    }

    // Table Style Setup Function
    UIManager.put("Table.background", panelColor);
    UIManager.put("Table.foreground", textColor);
    UIManager.put("Table.gridColor", new Color(70, 70, 70));
    UIManager.put("TableHeader.background", new Color(60, 60, 60));
    UIManager.put("TableHeader.foreground", accentColor);
    UIManager.put("TableHeader.font", font.deriveFont(Font.BOLD));

    // Theory Table
    theoryModel = new DefaultTableModel(new String[]{"Subject", "MID 1", "MID 2", "Avg MID", "Assignment", "Presentation", "Total Internal"}, 0);
    theoryTable = new JTable(theoryModel);
    theoryTable.setFont(font);
    theoryTable.setRowHeight(25);
    theoryTable.setBackground(panelColor);
    theoryTable.setForeground(textColor);
    theoryTable.setSelectionBackground(accentColor);
    theoryTable.setSelectionForeground(Color.WHITE);
    JScrollPane theoryScroll = new JScrollPane(theoryTable);
    theoryScroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(accentColor), "Theory Marks", 0, 0, font.deriveFont(Font.BOLD), accentColor));
    outputPanel.add(theoryScroll);

    // Lab Table
    labModel = new DefaultTableModel(new String[]{"Subject", "Int 1", "Int 2", "Avg Int", "Day-to-Day Eval", "Viva", "Project", "Total"}, 0);
    labTable = new JTable(labModel);
    labTable.setFont(font);
    labTable.setRowHeight(25);
    labTable.setBackground(panelColor);
    labTable.setForeground(textColor);
    labTable.setSelectionBackground(accentColor);
    labTable.setSelectionForeground(Color.WHITE);
    JScrollPane labScroll = new JScrollPane(labTable);
    labScroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(accentColor), "Lab Marks", 0, 0, font.deriveFont(Font.BOLD), accentColor));
    outputPanel.add(labScroll);

    add(outputPanel, BorderLayout.CENTER);

    // Button Action
    generateButton.addActionListener(e -> generateReport());

    getContentPane().setBackground(bgColor);
    inputPanel.setBackground(panelColor);
    outputPanel.setBackground(bgColor);
    theoryScroll.getViewport().setBackground(bgColor);
    labScroll.getViewport().setBackground(bgColor);

    setVisible(true);
}

       private void generateReport() {
           String roll = rollInput.getText().trim();
           if (roll.isEmpty()) {
               JOptionPane.showMessageDialog(this, "Please enter a roll number.");
               return;
           }

           Student student = generator.getStudents().get(roll);
           if (student == null) {
               JOptionPane.showMessageDialog(this, "No data found for roll number: " + roll);
               return;
           }

           // Update labels
           rollLabel.setText("Roll Number: " + student.getRollNumber());
           nameLabel.setText("Name: " + student.getName());

           // Clear and populate Theory Table
           theoryModel.setRowCount(0);
           Map<String, List<Double>> theoryMarks = student.getTheoryMarks();
           for (Map.Entry<String, List<Double>> entry : theoryMarks.entrySet()) {
               String subject = entry.getKey();
               List<Double> marks = entry.getValue();
               double mid1 = marks.get(0), mid2 = marks.get(1), assignment = marks.get(2), presentation = marks.get(3);
               double avgMid = Math.ceil((mid1 + mid2) / 2.0);
               double total = Math.ceil(avgMid + assignment + presentation);
               theoryModel.addRow(new Object[]{subject, mid1, mid2, avgMid, assignment, presentation, total});
           }

           // Clear and populate Lab Table
           labModel.setRowCount(0);
           Map<String, List<Double>> labMarks = student.getLabMarks();
           for (Map.Entry<String, List<Double>> entry : labMarks.entrySet()) {
               String subject = entry.getKey();
               List<Double> marks = entry.getValue();
               labModel.addRow(new Object[]{subject, marks.get(0), marks.get(1), marks.get(2), marks.get(3), marks.get(4), marks.get(5), marks.get(6)});
           }
       }
   }
   