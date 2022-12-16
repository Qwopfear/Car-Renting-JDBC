package carsharing;

import carsharing.controller.Controller;
import carsharing.dao.CompanyDAOImpl;
import carsharing.dataLayer.DataBaseConnection;

public class Main {

    public static void main(String[] args) {
        // write your code here

        DataBaseConnection dataBaseConnection = new DataBaseConnection(args.length > 0 ? args[1] : "myDB");
        CompanyDAOImpl dao = new CompanyDAOImpl(dataBaseConnection);
        Controller controller = new Controller(dao);
    }
}



