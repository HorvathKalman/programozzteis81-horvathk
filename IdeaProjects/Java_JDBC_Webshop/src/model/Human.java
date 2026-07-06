package model;

import java.util.ArrayList;
import java.util.List;

public class Human {

    private int id;
    private String name;
    private int capital;
    private List<Product> AllBoughtProducts;
    private List<Product> newBoughtProducts;


    public Human(int id, String name, int capital) {
        super();
        this.id = id;
        this.name = name;
        this.capital = capital;
        this.AllBoughtProducts = new ArrayList<>();
        this.newBoughtProducts = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapital() {
        return capital;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    public List<Product> getAllBoughtProducts() {
        return AllBoughtProducts;
    }

    public List<Product> getNewBoughtProducts() {
        return newBoughtProducts;
    }

    public void addNewBoughtProducts(Product product) {
        this.newBoughtProducts.add(product);
    }

    public void addAllBoughtProducts(Product product) {
        this.AllBoughtProducts.add(product);
    }

    public void resetNewBoughtProducts() {
        this.newBoughtProducts = new ArrayList<>();
    }

    public void deductCapital(Product product) {
        this.capital -= product.getPrice();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Human:\n");
        sb.append("id: ").append(id)
                .append(" | name: ").append(name)
                .append(" | capital: ").append(capital)
                .append("\n\n");

        sb.append("AllBoughtProducts:\n");

        if (AllBoughtProducts.isEmpty()) {
            sb.append("- (no products)\n");
        } else {
            for (Product p : AllBoughtProducts) {
                sb.append("- ").append(p).append("\n");
            }
        }

        return sb.toString();
    }
}
