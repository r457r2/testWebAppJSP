package com.mts.test;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by r457r on 04.12.2016.
 * Test bean
 */

public class TestDbController {
    private enum SortType {
        None, Name1Asc, Name1Desc, Name2Asc, Name2Desc;

        public static SortType countSortType(String sortField, String sortOrder) {
            SortType type = SortType.None;
            if (sortField != null && sortOrder != null) {
                switch (sortField.toLowerCase()) {
                    case "name1":
                        switch (sortOrder.toLowerCase()) {
                            case "asc":
                                type = SortType.Name1Asc;
                                break;
                            case "desc":
                                type = SortType.Name1Desc;
                                break;
                        }
                        break;
                    case "name2":
                        switch (sortOrder.toLowerCase()) {
                            case "asc":
                                type = SortType.Name2Asc;
                                break;
                            case "desc":
                                type = SortType.Name2Desc;
                                break;
                        }
                        break;
                }
            }
            return type;
        }
    }

    private static List<Record> data = new ArrayList<>();

    public TestDbController(){}

    public static List<Record> getData(String sortField, String sortOrder, String method) throws SQLException, NamingException {
        SortType type = SortType.countSortType(sortField, sortOrder);
        List<Record> result;
        String query = "SELECT id, name1, name2 FROM naming_pairs;";

        if (method != null && method.toLowerCase().equals("sql")) {
            switch (type) {
                case Name1Asc:
                    query = "SELECT id, name1, name2 FROM naming_pairs ORDER BY name1 ASC;";
                    break;
                case Name1Desc:
                    query = "SELECT id, name1, name2 FROM naming_pairs ORDER BY name1 DESC;";
                    break;
                case Name2Asc:
                    query = "SELECT id, name1, name2 FROM naming_pairs ORDER BY name2 ASC;";
                    break;
                case Name2Desc:
                    query = "SELECT id, name1, name2 FROM naming_pairs ORDER BY name1 DESC;";
                    break;
                case None:
                    break;
            }
            result = loadData(query);
        }
        else {
            result = loadData(query);
            switch (type) {
                case Name1Asc:
                    result.sort((o1, o2) -> o1.name1.compareTo(o2.name1));
                    break;
                case Name1Desc:
                    result.sort((o1, o2) -> o2.name1.compareTo(o1.name1));
                    break;
                case Name2Asc:
                    result.sort((o1, o2) -> o1.name2.compareTo(o2.name2));
                    break;
                case Name2Desc:
                    result.sort((o1, o2) -> o2.name2.compareTo(o1.name2));
                    break;
                case None:
                    break;
            }
        }

        return result;
    }

    private static List<Record> loadData(String query) throws NamingException, SQLException {
        List<Record> result = new ArrayList<>();

        InitialContext initCtx = new InitialContext();
        DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/MySQLtest");
        Connection con = ds.getConnection();
        Statement stm = con.createStatement();
        ResultSet res = stm.executeQuery(query);

        while (res.next()) {
            Record rec = new Record(res.getInt(1), res.getString(2), res.getString(3));
            result.add(rec);
        }

        stm.close();
        con.close();

        return result;
    }

    public static Boolean addRecord(String name1, String name2) {
        data.add(new Record(0, name1, name2));
        return true;
    }

    public static Boolean deleteRecord(Integer id) {
        return data.removeIf(record -> record.id.equals(id));
    }

    public static void updateRecord(Integer id, String name1, String name2) {
        data.replaceAll(record -> {
            if (record.id.equals(id)) {
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
