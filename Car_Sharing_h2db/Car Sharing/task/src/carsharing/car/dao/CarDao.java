package carsharing.car.dao;

import carsharing.car.model.Car;
import carsharing.company.model.Company;

import java.util.List;

public interface CarDao {
    List<Car> getCars();
    List<Car> getAvialableCars();
    List<Car> getCompanyCars(Company company);
    void insertCar(Car car);
}
