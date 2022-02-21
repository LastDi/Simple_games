package carsharing.customer.dao;

import carsharing.customer.controller.ConnectCustomer;
import carsharing.customer.model.Customer;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    List<Customer> customers;
    ConnectCustomer connect;

    public CustomerDaoImpl(ConnectCustomer connect) {
        this.customers = new ArrayList<>();
        this.connect = connect;

    }

    @Override
    public List<Customer> getCustomers() {
        customers = connect.getAllCustomer();
        return customers;
    }

    @Override
    public void insertCustomer(Customer customer) {
        connect.insertCustomer(customer);
    }

    @Override
    public void rentCar(Customer customer) {
        connect.rentCar(customer);
    }

    @Override
    public void returnCar(Customer customer) {
        connect.returnCar(customer);
    }
}
