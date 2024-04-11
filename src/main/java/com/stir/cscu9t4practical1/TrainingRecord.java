// An implementation of a Training Record as an ArrayList
package com.stir.cscu9t4practical1;

import java.util.*;

public class TrainingRecord {
    private List<Entry> tr;

    public TrainingRecord() {
        tr = new ArrayList<Entry>();
    } // constructor

    // add a record to the list - doesn't add if entry already exists
    public String addEntry(Entry e) {
        ListIterator<Entry> iter = tr.listIterator();

        boolean exists = false;

        while (iter.hasNext()) {
            Entry current = iter.next();
            if (current.getName().equals(e.getName()) && current.getDay() == e.getDay()
                    && current.getMonth() == e.getMonth() && current.getYear() == e.getYear()) {
                exists = true;
            }
        }

        if (!exists) {
            tr.add(e);
            return "Record Added\n";
        }

        return "Record already exists\n";
    } // addClass

    // look up the entry of a given day and month
    public String lookupEntry(int d, int m, int y) {
        ListIterator<Entry> iter = tr.listIterator();
        String result = "No entries found";
        while (iter.hasNext()) {
            Entry current = iter.next();
            if (current.getDay() == d && current.getMonth() == m && current.getYear() == y)
                result = current.getEntry();
        }
        return result;
    } // lookupEntry

    public String findByDate(int d, int m, int y) {
        ListIterator<Entry> iter = tr.listIterator();
        boolean flag = false;
        // String result = "No entries found";
        String result = "";
        while (iter.hasNext()) {
            Entry current = iter.next();
            if (current.getDay() == d && current.getMonth() == m && current.getYear() == y) {
                result += current.getEntry();
                flag = true;
            }
        }
        if (!flag)
            result = "No entries found";
        return result;
    }

    public String findByName(String name) {
        ListIterator<Entry> iter = tr.listIterator();
        String result = "";
        boolean flag = false;
        while (iter.hasNext()) {
            Entry current = iter.next();
            System.out.println(current.getName() + " == " + name);
            if (current.getName().toLowerCase().equals(name.toLowerCase())) {

                result += current.getEntry();
                flag = true;
            }
        }

        if (!flag) {
            result = "No entries found";
        }

        return result;
    }

    public String removeEntry(Entry e) {

        boolean removed = false;

        ListIterator<Entry> iter = tr.listIterator();

        while (iter.hasNext()) {
            Entry current = iter.next();
            int index = tr.indexOf(current);

            if (current.getName().equals(e.getName()) && current.getDay() == e.getDay()
                    && current.getMonth() == e.getMonth() && current.getYear() == e.getYear()) {
                tr.remove(index);
                removed = true;
                break;
            }
        }

        if (removed) {
            return "Entry " + e.getEntry() + " removed\n";
        }

        return "No entry found\n";
    }

    // Count the number of entries
    public int getNumberOfEntries() {
        return tr.size();
    }

    // Clear all entries
    public void clearAllEntries() {
        tr.clear();
    }

} // TrainingRecord