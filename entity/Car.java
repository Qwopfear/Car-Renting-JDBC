package carsharing.entity;

public class Car {
    private int id;
    private int companyId;
    private String name;


    public Car(int id, String name, int companyId) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
    }

    public Car(int companyId, String name) {
        this.companyId = companyId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
