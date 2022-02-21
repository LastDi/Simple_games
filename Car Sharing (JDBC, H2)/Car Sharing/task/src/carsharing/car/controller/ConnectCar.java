package carsharing.car.controller;

import carsharing.car.model.Car;
import carsharing.company.controller.Connect;
import carsharing.company.model.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectCar {
    private static Connection con = null;

    public void connect() {
            con = Connect.getCon();
    }

    public void createTable() {
        try (Statement stmt = con.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS Car "
                    + "(id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, "
                    + "name VARCHAR(30) NOT NULL UNIQUE, "
                    + "company_id INTEGER NOT NULL, "
                    + "FOREIGN KEY (company_id) REFERENCES Company(id) );";
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
    public void dropTable() {
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate("DROP TABLE Car;");
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public List<Car> getAllCompanyCars(Company company) {
        List<Car> cars = new ArrayList<>();
        String sql = String.format("SELECT * FROM Car WHERE company_id = %s;", company.getId());
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cars.add(new Car(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("company_id")));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return cars;
    }

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        String sql = String.format("SELECT * FROM Car;");
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cars.add(new Car(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("company_id")));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return cars;
    }

    public List<Car> getAvialableCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT car.id, car.name, car.company_id "
                + "FROM car LEFT JOIN customer "
                + "ON car.id = customer.rented_car_id "
                + "WHERE customer.name IS NULL; ";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cars.add(new Car(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("company_id")));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return cars;
    }


    public void insertCar(Car car) {
        String sql = String.format("INSERT INTO Car (name, company_id) values ('%s', %d)", car.getName(), car.getCompany_id());
        try (Statement stmt = con.createStatement();) {
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

}
