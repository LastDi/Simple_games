package carsharing.customer.dao;

import carsharing.car.model.Car;
import carsharing.customer.model.Customer;

import java.util.List;

public interface CustomerDao {
    List<Customer> getCustomers();
    void insertCustomer(Customer customer);
    void rentCar(Customer customer);
    void returnCar(Customer customer);
}
