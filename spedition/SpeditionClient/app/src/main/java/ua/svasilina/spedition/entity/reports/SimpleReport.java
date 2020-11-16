package ua.svasilina.spedition.entity.reports;

import java.util.LinkedList;

import ua.svasilina.spedition.entity.Driver;
import ua.svasilina.spedition.entity.Product;

public class SimpleReport extends IReport{

    private LinkedList<Product> products;
    private LinkedList<Driver> drivers;

    public SimpleReport() {
        products = new LinkedList<>();
        drivers = new LinkedList<>();
    }

    @Override
    public LinkedList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void addDriver(Driver driver) {
        drivers.add(driver);
    }

    public LinkedList<Driver> getDrivers() {
        return drivers;
    }
}
