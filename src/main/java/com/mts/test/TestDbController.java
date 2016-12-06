package com.mts.test;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
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

    public TestDbController(){}

    public static List<Record> getData(String sortField, String sortOrder, String method) throws SQLException, NamingException {
        SortType type = SortType.countSortType(sortField, sortOrder);
        List<Record> result;
        String query = "SELECT id, name1, name2 FROM naming_pairs;";

        if (method != null && method.toLowerCase().equals("sql")) {
            switch (type) {
                case Name1Asc:
                    query = "SELECT id, name1, name2 FROM naming_pairs ORDER BY name1 ASC;"; break;
                case Name1Desc:
                    query = "SELECT id, name1, name2 FROM naming_pairs ORDER BY name1 DESC;"; break;
                case Name2Asc:
                    query = "SELECT id, name1, name2 FROM naming_pairs ORDER BY name2 ASC;"; break;
                case Name2Desc:
                    query = "SELECT id, name1, name2 FROM naming_pairs ORDER BY name2 DESC;"; break;
                case None: break;
            }
            result = loadData(query);
        }
        else {
            result = loadData(query);
            switch (type) {
                case Name1Asc:
                    result.sort((o1, o2) -> o1.name1.compareTo(o2.name1)); break;
                case Name1Desc:
                    result.sort((o1, o2) -> o2.name1.compareTo(o1.name1)); break;
                case Name2Asc:
                    result.sort((o1, o2) -> o1.name2.compareTo(o2.name2)); break;
                case Name2Desc:
                    result.sort((o1, o2) -> o2.name2.compareTo(o1.name2)); break;
                case None: break;
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

    public static void insertRecord(String name1, String name2) throws NamingException, SQLException {
        InitialContext initCtx = new InitialContext();
        DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/MySQLtest");

        Connection con = ds.getConnection();
        PreparedStatement stm = con.prepareStatement("INSERT INTO naming_pairs(name1, name2) VALUES (?, ?);");
        stm.setString(1, name1);
        stm.setString(2, name2);
        stm.executeUpdate();

        stm.close();
        con.close();
    }

    public static void deleteRecord(Integer id) throws NamingException, SQLException {
        InitialContext initCtx = new InitialContext();
        DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/MySQLtest");

        Connection con = ds.getConnection();
        PreparedStatement stm = con.prepareStatement("DELETE FROM naming_pairs WHERE id = ?;");
        stm.setInt(1, id);
        stm.executeUpdate();

        stm.close();
        con.close();
    }

    public static void updateRecord(Integer id, String name1, String name2) throws NamingException, SQLException {
        InitialContext initCtx = new InitialContext();
        DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/MySQLtest");

        Connection con = ds.getConnection();
        PreparedStatement stm = con.prepareStatement("UPDATE naming_pairs SET name1 = ?, name2 = ? WHERE id = ?;");
        stm.setString(1, name1);
        stm.setString(2, name2);
        stm.setInt(3, id);
        stm.executeUpdate();

        stm.close();
        con.close();
    }
}
