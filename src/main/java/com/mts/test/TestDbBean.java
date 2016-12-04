package com.mts.test;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by r457r on 04.12.2016.
 * Test bean
 */
public class TestDbBean {
    private static List<Record> data = new ArrayList<>();
     static {
        data.add(new Record(1, "Artur", "Bol"));
        data.add(new Record(2, "Barton", "Ark"));
        data.add(new Record(3, "Berk", "Cole"));
        data.add(new Record(4, "Code", "Dare"));
        data.add(new Record(5, "Ang", "Black"));
    }

    public TestDbBean(){}

    public static List<Record> getData(String sortField, String sortOrder) {
        List<Record> result = data;

        if (sortOrder != null && sortOrder.toUpperCase().equals("DESC"))
            result.sort((o1, o2) -> getRecVal(o2, sortField).compareTo(getRecVal(o1, sortField)));
        else
            result.sort((o1, o2) -> getRecVal(o1, sortField).compareTo(getRecVal(o2, sortField)));

        return result;
    }

    public static Boolean addRecord(String name1, String name2) {
        data.add(new Record(0, name1, name2));
        return true;
    }

    public static Boolean deleteRecord(Integer id) {
        return data.removeIf(record -> record.id == id);
    }

    public static void updateRecord(Integer id, String name1, String name2) {
        data.replaceAll(record -> {
            if (record.id == id) {
                return new Record(id, name1, name2);
            } else {
                return record;
            }
        });
    }

    private static String getRecVal(Record rec, String valName) {
        String res = "";
        if (valName != null)
            switch (valName) {
                case "name1":
                    res = rec.name1;
                    break;
                case "name2":
                    res = rec.name2;
                    break;
            }
        return res;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
