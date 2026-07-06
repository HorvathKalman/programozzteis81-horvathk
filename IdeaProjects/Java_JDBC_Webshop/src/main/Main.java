package main;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import db.Database;
import model.Human;
import model.Product;

public class Main {

    public static void main(String[] args) throws SQLException {

        Database db = new Database();

        List<Product> productList = db.findAllProduct();
        System.out.println("Products available:");
        for (Product product : productList) {
            System.out.println(product);
        }
        System.out.println();

        Human human = db.getHumanById(1);
        System.out.println(human);


        db.closeDb();

        boolean successBuy = true;
        Scanner scanner = new Scanner(System.in);

        do {
            Product selectedProduct = null;

            while (selectedProduct == null) {
                System.out.println("Please select a product by ID:");
                int selectedID = scanner.nextInt();

                for (Product IterProduct : productList) {

                    if (IterProduct.getId() == selectedID) {
                        selectedProduct = IterProduct;
                        break;
                    }
                }
            }

            if (human.getCapital() >= selectedProduct.getPrice()) {
                human.deductCapital(selectedProduct);
                human.addNewBoughtProducts(selectedProduct);
                System.out.println("Bought item: " + selectedProduct.getName());
                System.out.println("Current balance: " + human.getCapital());
            } else {
                System.out.println("Insufficient funds!");
                successBuy = false;
            }

        } while (successBuy);
        System.out.println(human);

        db = new Database();
        db.updateHuman(human);
        human.resetNewBoughtProducts();
        db.closeDb();

    }
}


