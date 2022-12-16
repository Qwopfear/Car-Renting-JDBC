package carsharing.controller;

import carsharing.dao.CompanyDAOImpl;
import carsharing.entity.Car;
import carsharing.entity.Company;
import carsharing.entity.Customer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Controller {

    private final CompanyDAOImpl dao;


    public Controller(CompanyDAOImpl dao) {
        this.dao = dao;
        showMainMenu();
    }


    void showMainMenu() {
        System.out.println("1. Log in as a manager");
        System.out.println("2. Log in as a customer");
        System.out.println("3. Create a customer");
        System.out.println("0. Exit");

        switch (new Scanner(System.in).nextLine()) {
            case "1": {
                showManagerMenu();
                break;
            }
            case "2": {
                showCustomerMenu();
                break;
            }
            case "3": {
                createCustomer();
                break;
            }
            default:
                System.exit(0);
        }

    }

    private void createCustomer() {
        System.out.println("Enter the customer name:");
        dao.saveCustomer(new Customer(new Scanner(System.in).nextLine()));
        System.out.println("The customer was added!");

        showMainMenu();
    }

    private void showCustomerMenu() {
        if (dao.getAllCustomers().isEmpty()) {
            System.out.println("The customer list is empty! \n");
            showMainMenu();
        } else {
            chooseCustomer();
        }
    }

    private void chooseCustomer() {

        System.out.println("Customer list:");
        for (int i = 1; i <= dao.getAllCustomers().size(); i++) {
            System.out.println(i + ". " + dao.getAllCustomers().get(i - 1).getName());
        }
        System.out.println("0. Back");

        int clientNumber = new Scanner(System.in).nextInt();
        if (clientNumber != 0) {
            showRentMenu(dao.getAllCustomers().get(clientNumber - 1).getId());
        } else {
            showMainMenu();
        }
    }

    private void showRentMenu(int customerId) {
        Customer customer = dao.getCustomer(customerId);

        System.out.println("1. Rent a car\n" +
                "2. Return a rented car\n" +
                "3. My rented car\n" +
                "0. Back");

        switch (new Scanner(System.in).nextLine()) {
            case "1": {
                if (customer.getCarId() != 0) {
                    System.out.println("You've already rented a car!");
                } else {
                    rentCar(customer);
                }
                showRentMenu(customer.getId());
                break;
            }
            case "2": {
                returnCar(customer);
                showRentMenu(customer.getId());
                break;
            }
            case "3": {
                showRentedCar(customer);
                showRentMenu(customer.getId());
            }
            default:
                showMainMenu();
        }
    }

    private void returnCar(Customer customer) {
        if (customer.getCarId() == 0) {
            System.out.println("You didn't rent a car!");
        } else {
            dao.addCarToCustomer(customer.getId(), 0);
            System.out.println("You've returned a rented car!");
        }
    }

    private void showRentedCar(Customer customer) {
        customer = dao.getCustomer(customer.getId());
        Car car = null;
        Company company = null;
        if (customer.getCarId() > 0) {
            car = dao.getCar(customer.getCarId());
            company = dao.getCompany(car.getCompanyId());
            System.out.println("Your rented car:\n" +
                    car.getName() + "\n" +
                    "Company:\n" +
                    company.getName());
        } else {
            System.out.println("You didn't rent a car!");
        }


    }

    private void rentCar(Customer customer) {
        chooseCompany(customer);
    }

    private void showManagerMenu() {


        System.out.println("1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back");

        switch (new Scanner(System.in).nextLine()) {
            case "1": {
                if (dao.getAllCompanies().isEmpty()) {
                    System.out.println("The company list is empty!");
                } else {
                    chooseCompany(null);

                }
                showManagerMenu();
                break;
            }
            case "2": {
                System.out.println("Enter the company name:");
                dao.saveCompany(new Company(new Scanner(System.in).nextLine()));
                System.out.println("The company was created!");
                showManagerMenu();
                break;
            }
            default:
                showMainMenu();
        }
    }

    private void chooseCompany(Customer customer) {
        System.out.println("Choose a company: ");
        dao.getAllCompanies().forEach(System.out::println);
        System.out.println("0. Back");
        System.out.println();
        int companyNumber = new Scanner(System.in).nextInt();
        if (customer == null && companyNumber != 0) {
            System.out.println(dao.getCompany(companyNumber).getName() + " company: ");
            showCompanyMenu(dao.getCompany(companyNumber));
        } else if (customer != null && companyNumber != 0) {
            showCompanyCars(dao.getCompany(companyNumber), customer);
            System.out.println("0. Back");
            completeRent(customer, dao.getCompany(companyNumber).getCars());
        } else {
            showManagerMenu();
        }
    }

    private void completeRent(Customer customer, List<Car> cars) {
        int carNumber = new Scanner(System.in).nextInt();

        if (carNumber != 0) {
            System.out.println("You rented '" + cars.get(carNumber - 1).getName() + "'");
            dao.addCarToCustomer(customer.getId(), cars.get(carNumber - 1).getId());
        }

    }

    private void showCompanyMenu(Company company) {

        company = dao.getCompany(company.getId());
        System.out.println("1. Car list");
        System.out.println("2. Create a car");
        System.out.println("0. Back");
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        String choose = scanner.nextLine();
        switch (choose) {
            case "1": {
                showCompanyCars(company, null);
                System.out.println();
                showCompanyMenu(company);
                break;
            }
            case "2": {
                System.out.println("Enter the car name:");
                dao.saveCar(new Car(company.getId(), scanner.nextLine()));
                System.out.println("The car was created!");
                showCompanyMenu(company);
                break;
            }
            default:
                showManagerMenu();
        }

    }

    void showCompanyCars(Company company, Customer customer) {
        if (company.getCars().isEmpty()) {
            System.out.println("The car list is empty!");
        } else {
            System.out.println("Car list:");
            List<Car> cars = company.getCars();
            if (customer != null) {
                cars = dao.getAllAvailableCars();
            }


            cars.sort(Comparator.comparingInt(Car::getId));
            for (int i = 1; i <= cars.size(); i++) {
                System.out.println(i + ". " + cars.get(i - 1).getName());
            }

        }
    }

}
