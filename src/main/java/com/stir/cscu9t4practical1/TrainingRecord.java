// An implementation of a Training Record as an ArrayList
package com.stir.cscu9t4practical1;

import java.util.*;
import java.util.Calendar;

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

    // Finding entry by entered date
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

    // Finding entry by name (case-insensitive)
    public String findByName(String name) {
        ListIterator<Entry> iter = tr.listIterator();
        String result = "";
        // if a result was found
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

    // removing entry by name and date
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

    // private method to verify a week before current date
    private boolean between(Entry e, HashMap<String, Integer> pastWeek) {
        Calendar current = Calendar.getInstance();

        boolean flag = false;

        if (e.getDay() >= pastWeek.get("day") && e.getDay() <= current.get(Calendar.DAY_OF_MONTH)) {
            flag = true;
        }
        if (e.getMonth() >= pastWeek.get("month") && e.getMonth() <= (current.get(Calendar.MONTH) + 1)) {
            flag = true;
        }
        if (e.getYear() >= pastWeek.get("year") && e.getYear() <= current.get(Calendar.YEAR)) {
            flag = true;
        }

        return flag;

    }

    // getting weekly distance
    public String weeklyDistance(String name) {
        Calendar current = Calendar.getInstance();
        current.add(Calendar.DAY_OF_MONTH, -7);

        HashMap<String, Integer> week = new HashMap<>();
        week.put("day", current.get(Calendar.DAY_OF_MONTH));
        week.put("month", current.get(Calendar.MONTH) + 1);
        week.put("year", current.get(Calendar.YEAR));

        boolean flag = false;
        float dist = 0;

        ListIterator<Entry> iter = tr.listIterator();

        while (iter.hasNext()) {
            Entry temp = iter.next();
            if (temp.getName().equals(name) && between(temp, week)) {
                flag = true;
                dist += temp.getDistance();
            }
        }

        if (flag) {
            return name + " ran " + dist + " km\n";
        }

        return "No entry found";
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