package carsharing.entity;

import java.util.ArrayList;
import java.util.List;

public class Company {

    private int id;
    private String name;

    private List<Car> cars;

    public Company(int id, String name) {
        this.id = id;
        this.name = name;
        this.cars = new ArrayList<>();
    }


    public Company( String name) {

        this.name = name;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCar(Car c) {
        cars.add(c);
    }

    public List<Car> getCars() {
        return cars;
    }

    @Override
    public String toString() {
        return id + ". " + name;
    }
}
