package carsharing.company.controller;

import carsharing.company.model.Company;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Connect {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:./Car Sharing/task/src/carsharing/db/";
    private static Connection con = null;

    public void connect(String DBname) {
        try {
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(DB_URL + DBname);
            con.setAutoCommit(true);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        try (Statement stmt = con.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS Company "
                    + "(id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, "
                    + "name VARCHAR(30) NOT NULL UNIQUE);";
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void dropTable() {
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate("DROP TABLE Company;");
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<>();
        String sql = "SELECT * FROM Company;";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                companies.add(new Company(rs.getInt("id"),
                        rs.getString("name")));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return companies;
    }

    public void insertCompany(Company company) {
        String sql = String.format("INSERT INTO company (name) values ('%s')", company.getName());
        try (Statement stmt = con.createStatement();) {
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            if (con != null)
                con.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static Connection getCon() {
        return con;
    }
}
