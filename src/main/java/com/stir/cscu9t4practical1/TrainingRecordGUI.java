// GUI and main program for the Training Record
package com.stir.cscu9t4practical1;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.lang.Number;

// TODO: starting 9

public class TrainingRecordGUI extends JFrame implements ActionListener {

    private JTextField name = new JTextField(30);
    private JTextField day = new JTextField(2);
    private JTextField month = new JTextField(2);
    private JTextField year = new JTextField(4);
    private JTextField hours = new JTextField(2);
    private JTextField mins = new JTextField(2);
    private JTextField secs = new JTextField(2);
    private JTextField dist = new JTextField(4);
    private JTextField repetition = new JTextField(3);
    private JTextField recovery = new JTextField(3);
    private JTextField where = new JTextField(10);
    private JTextField terrain = new JTextField(10);
    private JTextField tempo = new JTextField(10);

    private JLabel labn = new JLabel(" Name:");
    private JLabel labd = new JLabel(" Day:");
    private JLabel labm = new JLabel(" Month:");
    private JLabel laby = new JLabel(" Year:");
    private JLabel labh = new JLabel(" Hours:");
    private JLabel labmm = new JLabel(" Mins:");
    private JLabel labs = new JLabel(" Secs:");
    private JLabel labdist = new JLabel(" Distance (km):");
    private JLabel labRepetition = new JLabel("Repetition:");
    private JLabel labRecovery = new JLabel("Recovery:");
    private JLabel labWhere = new JLabel("Where:");
    private JLabel labTerrain = new JLabel("Terrain:");
    private JLabel labTempo = new JLabel("Tempo:");

    private JButton addR = new JButton("Add");
    private JButton lookUpByDate = new JButton("Look Up");
    private JButton findAllByDate = new JButton("Find All By Date");
    private JButton findAllByName = new JButton("Find All By Name");
    private JButton removeR = new JButton("Remove");

    private JComboBox entryType = new JComboBox<>(new String[] { "Generic", "Sprint", "Swim", "Cycle" });

    private TrainingRecord myAthletes = new TrainingRecord();

    private JTextArea outputArea = new JTextArea(5, 50);

    public static void main(String[] args) {
        TrainingRecordGUI applic = new TrainingRecordGUI();
    } // main

    // set up the GUI
    public TrainingRecordGUI() {
        super("Training Record");
        setLayout(new FlowLayout());
        add(entryType);
        entryType.addActionListener(this);
        add(labn);
        add(name);
        name.setEditable(true);
        add(labd);
        add(day);
        day.setEditable(true);
        add(labm);
        add(month);
        month.setEditable(true);
        add(laby);
        add(year);
        year.setEditable(true);
        add(labh);
        add(hours);
        hours.setEditable(true);
        add(labmm);
        add(mins);
        mins.setEditable(true);
        add(labs);
        add(secs);
        secs.setEditable(true);
        add(labdist);
        add(dist);
        dist.setEditable(true);

        add(addR);
        addR.addActionListener(this);
        add(lookUpByDate);
        lookUpByDate.addActionListener(this);
        findAllByDate.addActionListener(this);
        add(findAllByDate);

        add(findAllByName);
        findAllByName.addActionListener(this);

        add(removeR);
        removeR.addActionListener(this);

        add(outputArea);
        outputArea.setEditable(false);

        setSize(720, 200);
        setVisible(true);
        blankDisplay();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // To save typing in new entries while testing, uncomment
        // the following lines (or add your own test cases)

    } // constructor

    // listen for and respond to GUI events
    public void actionPerformed(ActionEvent event) {
        String message = "";
        if (event.getSource() == addR) {
            message = addEntry(entryType.getSelectedItem().toString());
        }
        if (event.getSource() == lookUpByDate) {
            message = lookupEntry();
            // System.out.println(message);
        }
        if (event.getSource() == findAllByDate) {
            // System.out.println("Not implemented yet");
            message = findByDate();
        }
        if (event.getSource() == entryType) {
            comboBoxAction();
        }
        if (event.getSource() == findAllByName) {
            message = findByName();
        }
        if (event.getSource() == removeR) {
            message = removeEntry();
        }
        outputArea.setText(message);
        blankDisplay();
    } // actionPerformed

    public String addEntry(String what) {
        String message = "Record added\n";
        System.out.println("Adding " + what.toLowerCase() + " entry to the records");
        String n = name.getText();
        int m = Integer.parseInt(month.getText());
        int d = Integer.parseInt(day.getText());
        int y = Integer.parseInt(year.getText());
        float km = java.lang.Float.parseFloat(dist.getText());
        int h = Integer.parseInt(hours.getText());
        int mm = Integer.parseInt(mins.getText());
        int s = Integer.parseInt(secs.getText());
        Entry e = new Entry(n, d, mm, y, h, m, s, s);
        if (what == "Generic") {
            e = new Entry(n, d, m, y, h, mm, s, km);
        } else if (what == "Sprint") {
            int rep = Integer.parseInt(repetition.getText());
            int rec = Integer.parseInt(recovery.getText());
            e = new SprintEntry(n, d, m, y, h, mm, s, km, rep, rec);
        } else if (what == "Swim") {
            String w = where.getText();
            e = new SwimEntry(n, d, m, y, h, mm, s, km, w);
        } else if (what == "Cycle") {
            String ter = terrain.getText();
            String tem = tempo.getText();
            e = new CycleEntry(n, d, m, y, h, mm, s, s, ter, tem);
        }

        message = myAthletes.addEntry(e);
        return message;
    }

    public int[] parseDates() {
        int[] dates = { 0, 0, 0, 0 };

        try {
            int d = Integer.parseInt(day.getText());
            if (d <= 0) {
                throw new Exception();
            }
            dates[0] = d;
        } catch (Exception e) {
            // System.out.println(e);
            // JOptionPane o = new JOptionPane();
            JOptionPane.showMessageDialog(this, "Error while reading day", "Error", JOptionPane.ERROR_MESSAGE);
            dates[3] = -1;
            return dates;
        }

        try {
            int m = Integer.parseInt(month.getText());
            if (m <= 0) {
                throw new Exception();
            }
            dates[1] = m;
        } catch (Exception e) {
            // System.out.println(e);
            // JOptionPane o = new JOptionPane();
            JOptionPane.showMessageDialog(this, "Error while reading month", "Error", JOptionPane.ERROR_MESSAGE);
            dates[3] = -1;
            return dates;

        }

        try {
            int y = Integer.parseInt(year.getText());
            if (y <= 0) {
                throw new Exception();
            }
            dates[2] = y;
        } catch (Exception e) {
            // System.out.println(e);
            // JOptionPane o = new JOptionPane();
            JOptionPane.showMessageDialog(this, "Error while reading year", "Error", JOptionPane.ERROR_MESSAGE);
            dates[3] = -1;
            return dates;

        }

        return dates;
    }

    public String lookupEntry() {
        int[] dates = parseDates();
        if (dates[3] == -1) {
            return "";
        }
        outputArea.setText("looking up record ...");
        String message = myAthletes.lookupEntry(dates[0], dates[1], dates[2]);
        return message;
    }

    public String findByDate() {
        // int d = Integer.parseInt(day.getText());
        // int m = Integer.parseInt(month.getText());
        // int y = Integer.parseInt(year.getText());
        int[] dates = parseDates();
        if (dates[3] == -1) {
            return "";
        }
        outputArea.setText("finding records by date: " + dates[0] + "/" + dates[1] + "/" + dates[2]);
        String message = myAthletes.findByDate(dates[0], dates[1], dates[2]);
        return message;
    }

    public String findByName() {
        return myAthletes.findByName(name.getText());
    }

    public void comboBoxAction() {
        String type = entryType.getSelectedItem().toString();

        if (type == "Generic") {
            remove(labRepetition);
            remove(repetition);
            remove(labRecovery);
            remove(recovery);
            remove(labWhere);
            remove(where);
            remove(labTerrain);
            remove(terrain);
            remove(labTempo);
            remove(tempo);

            rootPane.revalidate();
            rootPane.repaint();

        } else if (type == "Sprint") {
            remove(labWhere);
            remove(where);
            remove(labTerrain);
            remove(terrain);
            remove(labTempo);
            remove(tempo);

            add(labRepetition, 17);
            add(repetition, 18);
            add(labRecovery, 19);
            add(recovery, 20);

            rootPane.revalidate();
            rootPane.repaint();

        } else if (type == "Swim") {
            remove(labRepetition);
            remove(repetition);
            remove(labRecovery);
            remove(recovery);
            remove(labTerrain);
            remove(terrain);
            remove(labTempo);
            remove(tempo);

            add(labWhere, 17);
            add(where, 18);

            rootPane.revalidate();
            rootPane.repaint();
        } else if (type == "Cycle") {
            remove(labRepetition);
            remove(repetition);
            remove(labRecovery);
            remove(recovery);
            remove(labWhere);
            remove(where);

            add(labTerrain, 17);
            add(terrain, 18);
            add(labTempo, 19);
            add(tempo, 20);

            rootPane.revalidate();
            rootPane.repaint();
        }
    }

    public String removeEntry() {
        int[] dates = parseDates();
        if (dates[3] == -1) {
            return "";
        }

        Entry temp = new Entry(name.getText(), dates[0], dates[1], dates[2], 0, 0, 0, 0);
        return myAthletes.removeEntry(temp);
    }

    public void blankDisplay() {
        name.setText("");
        day.setText("");
        month.setText("");
        year.setText("");
        hours.setText("");
        mins.setText("");
        secs.setText("");
        dist.setText("");
        repetition.setText("");
        recovery.setText("");
        where.setText("");
        terrain.setText("");
        tempo.setText("");

    }// blankDisplay
     // Fills the input fields on the display for testing purposes only

    public void fillDisplay(Entry ent) {
        name.setText(ent.getName());
        day.setText(String.valueOf(ent.getDay()));
        month.setText(String.valueOf(ent.getMonth()));
        year.setText(String.valueOf(ent.getYear()));
        hours.setText(String.valueOf(ent.getHour()));
        mins.setText(String.valueOf(ent.getMin()));
        secs.setText(String.valueOf(ent.getSec()));
        dist.setText(String.valueOf(ent.getDistance()));
    }

} // TrainingRecordGUI
