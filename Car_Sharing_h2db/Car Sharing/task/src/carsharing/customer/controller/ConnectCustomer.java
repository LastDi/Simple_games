package carsharing.customer.controller;

import carsharing.car.model.Car;
import carsharing.company.controller.Connect;
import carsharing.customer.model.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConnectCustomer {
    private static Connection con = null;

    public void connect() {
        con = Connect.getCon();
    }

    public void createTable() {
        try (Statement stmt = con.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS Customer "
                    + "(id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, "
                    + "name VARCHAR(30) NOT NULL UNIQUE, "
                    + "rented_car_id INTEGER, "
                    + "FOREIGN KEY (rented_car_id) REFERENCES Car(id) );";
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void dropTable() {
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate("DROP TABLE Customer;");
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public List<Customer> getAllCustomer() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customer;";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                customers.add(new Customer(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("rented_car_id")));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return customers;
    }

    public void insertCustomer(Customer customer) {
        String sql = String.format("INSERT INTO Customer (name) values ('%s');", customer.getName());
        try (Statement stmt = con.createStatement();) {
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public Car getCar(Customer customer) {
        String sql = String.format("SELECT name FROM Car where id = %d;", customer.getRented_car_id());
        Car car = null;
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                car = new Car(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("company_id"));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return car;
    }

    public void rentCar(Customer customer) {
        String sql = String.format("UPDATE Customer SET rented_car_id = %d WHERE id = %d;", customer.getRented_car_id(), customer.getId());
        try (Statement stmt = con.createStatement();) {
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void returnCar(Customer customer) {
        String sql = String.format("UPDATE Customer SET rented_car_id = NULL WHERE id = %d;", customer.getId());
        try (Statement stmt = con.createStatement();) {
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
