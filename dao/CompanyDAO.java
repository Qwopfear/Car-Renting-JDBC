package carsharing.dao;

import carsharing.entity.Car;
import carsharing.entity.Company;
import carsharing.entity.Customer;

import java.util.List;

public interface CompanyDAO {

    List<Company> getAllCompanies();
    List<Car> getAllCars();
    List<Car> getAllAvailableCars();
    List<Customer> getAllCustomers();
    Company getCompany(int companyId);

    Car getCar(int carId);
    Customer getCustomer(int customerId);

    void saveCar(Car car);

    void saveCustomer(Customer customer);
    void saveCompany(Company company);

    void addCarToCustomer(int customerId, int carId);

}
