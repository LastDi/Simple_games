package carsharing.car.dao;

import carsharing.car.controller.ConnectCar;
import carsharing.car.model.Car;
import carsharing.company.model.Company;

import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao{
    List<Car> cars;
    ConnectCar connect;

    public CarDaoImpl(ConnectCar connect) {
        this.cars = new ArrayList<>();
        this.connect = connect;
    }

    @Override
    public List<Car> getAvialableCars() {
        cars = connect.getAvialableCars();
        return cars;
    }

    @Override
    public List<Car> getCompanyCars(Company company) {
        cars = connect.getAllCompanyCars(company);
        return cars;
    }

    @Override
    public List<Car> getCars() {
        cars = connect.getAllCars();
        return cars;
    }

    @Override
    public void insertCar(Car car) {
        connect.insertCar(car);
    }
}
