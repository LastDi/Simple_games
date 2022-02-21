package carsharing.view;

import carsharing.car.controller.ConnectCar;
import carsharing.car.dao.CarDao;
import carsharing.car.dao.CarDaoImpl;
import carsharing.car.model.Car;
import carsharing.company.controller.Connect;
import carsharing.company.dao.CompanyDao;
import carsharing.company.dao.CompanyDaoImpl;
import carsharing.company.model.Company;
import carsharing.customer.controller.ConnectCustomer;
import carsharing.customer.dao.CustomerDao;
import carsharing.customer.dao.CustomerDaoImpl;
import carsharing.customer.model.Customer;

import java.util.List;
import java.util.Scanner;

public class Carsharing {
    private List<Company> companies;
    private List<Car> cars;
    private List<Customer> customers;
    private Scanner sc;
    private CompanyDao companyDao;
    private CarDao carDao;
    private CustomerDao customerDao;
    private Company company;
    private Customer customer;
    private Car car;
    private boolean customerChoose = false;

    public void startMenu(String DBname) {
        Connect connect = new Connect();
        ConnectCar connectCar = new ConnectCar();
        ConnectCustomer connectCustomer = new ConnectCustomer();
        companyDao = new CompanyDaoImpl(connect);
        customerDao = new CustomerDaoImpl(connectCustomer);
        carDao = new CarDaoImpl(connectCar);
        connect.connect(DBname);
        connectCar.connect();
        connectCustomer.connect();
        connect.createTable();
        connectCar.createTable();
        connectCustomer.createTable();
        printStartMenu();
        sc = new Scanner(System.in);
        handleInput();
        sc.close();
        connect.closeConnection();
    }

    private void handleInput() {
        int ans = sc.nextInt();
        while (ans != 0) {
            if (ans == 1)
                logAsManager();
            if (ans == 2)
                logAsCustomer();
            if (ans == 3)
                create(Type.CUSTOMER);
           printStartMenu();
           ans = sc.nextInt();
        }
    }

    private void create(Type type) {
        System.out.printf("Enter the %s name:\n", type.getTitle());
        sc.nextLine();
        String name = sc.nextLine();
        switch (type) {
            case COMPANY:
                companyDao.insertCompany(new Company(name));
                break;
            case CAR:
                carDao.insertCar(new Car(name, company.getId()));
                break;
            case CUSTOMER:
                customerDao.insertCustomer(new Customer(name));
                break;
        }
        System.out.printf("The %s was created!\n", type.getTitle());
    }


    private void logAsManager() {
        customerChoose = false;
        printManagerMenu();
        int ans = sc.nextInt();
        while (ans != 0) {
            if (ans == 1)
                companyList();
            if (ans == 2)
                create(Type.COMPANY);
            printManagerMenu();
            ans = sc.nextInt();
        }
    }

    private void logAsCustomer() {
        customers = customerDao.getCustomers();
        if (customers.isEmpty())
            System.out.println("The customer list is empty!");
        else {
            printCustomerMenu();
            int ans = sc.nextInt();
            if (ans != 0) {
                printCustomerInfo();
                customer = customers.get(ans - 1);
                handleCustomerInput();
            }
        }
    }

    private void handleCustomerInput() {
        int ans = sc.nextInt();
        cars = carDao.getCars();
        while (ans != 0) {
            if (ans == 1)
                rentCar();
            if (ans == 2)
                returnRentCar();
            if (ans == 3)
                rentedCars();
            printCustomerInfo();
            ans = sc.nextInt();
        }
    }

    private void rentedCars() {
        if (customer.getRented_car_id() == 0)
            System.out.println("You didn't rent a car!");
        else {
            car = cars.get(customer.getRented_car_id() - 1);
            cars.stream().map(Car::getCompany_id).forEach(System.out::println);
            company = companyDao.getCompanies().get(car.getCompany_id() - 1);
            System.out.printf("Your rented car:\n" +
                    "%s\n" +
                    "Company:\n" +
                    "%s\n", car, company.getName());
        }
    }

    private void returnRentCar() {
        if (customer.getRented_car_id() == 0)
            System.out.println("You didn't rent a car!");
        else {
            customer.setRented_car_id(0);
            customerDao.returnCar(customer);
            System.out.println("You've returned a rented car!");
            car = null;
        }
    }

    private void rentCar() {
        if (customer.getRented_car_id() != 0) {
            System.out.println("You've already rented a car!");
            return;
        }
        customerChoose = true;
        companyList();
        int ans = sc.nextInt();
        if (ans != 0) {
            car = carDao.getCompanyCars(company).get(ans - 1);
            customer.setRented_car_id(car.getId());
            customerDao.rentCar(customer);
            System.out.printf("You rented '%s'\n", car);
        }
    }

    private void companyList() {
        companies = companyDao.getCompanies();
        if (companies.isEmpty())
            System.out.println("The company list is empty!");
        else {
            printChooseCompanyMenu();
            companyChoose();
        }
    }

    private void companyChoose() {
        int ans = sc.nextInt();
        if (ans != 0) {
            company = companies.get(ans - 1);
            if (!customerChoose) {
                printCompanyInfo();
                inCompanyMenu();
            }
            else {
                carList();
            }
        }
    }

    private void inCompanyMenu() {
        int ans = sc.nextInt();
        while (ans != 0) {
            if (ans == 1)
                carList();
            if (ans == 2)
                create(Type.CAR);
            printCompanyInfo();
            ans = sc.nextInt();
        }
    }

    private void carList() {
        cars = carDao.getCompanyCars(company);
        if (cars.isEmpty())
            System.out.println("The car list is empty!");
        else {
            System.out.println("Car list:");
            int ind = 1;
            if (!customerChoose)
                for (Car car : cars) {
                    if (car.getCompany_id() == company.getId())
                        System.out.printf("%d. %s\n", ind++, car);
                }
            else {
                cars = carDao.getAvialableCars();
                for (Car car : cars) {
                    if (car.getCompany_id() == company.getId())
                        System.out.printf("%d. %s\n", ind++, car);
                }
            }
            if (customerChoose)
                System.out.println("0. Back");
        }
    }

    private void printStartMenu() {
        System.out.println("1. Log in as a manager\n"
                + "2. Log in as a customer\n"
                + "3. Create a customer\n"
                + "0. Exit");
    }

    private void printManagerMenu() {
        System.out.println("1. Company list\n" +
                "2. Create a company\n" +
                "0. Back");
    }

    private void printChooseCompanyMenu() {
        System.out.println("Choose the company:");
        //companies.forEach(System.out::println);
        int ind = 1;
        for (Company company : companies) {
            System.out.printf("%d. %s\n", ind++, company.getName());
        }
        System.out.println("0. Back");
    }

    private void printCompanyInfo() {
        System.out.printf("'%s' company\n", company.getName());
        System.out.println("1. Car list\n2. Create a car\n0. Back");
    }

    private void printCustomerInfo(){
        System.out.println("1. Rent a car\n" +
                "2. Return a rented car\n" +
                "3. My rented car\n" +
                "0. Back");
    }

    private void printCustomerMenu() {
        System.out.println("Customer list:");
        customers.forEach(System.out::println);
        System.out.println("0. Back");
    }

}
