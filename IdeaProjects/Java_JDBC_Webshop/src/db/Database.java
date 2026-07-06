package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Human;
import model.Product;

public class Database {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/webshopdb";
    private static final String DB_PWD = "root";
    private static final String DB_USER = "root";
    private Connection conn;

    public Database() throws SQLException {
        this.conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
    }

    public void closeDb() throws SQLException {
        this.conn.close();
    }

    public List<Product> findAllProduct() throws SQLException {

        List<Product> productList = new ArrayList<>();

        PreparedStatement ps = conn.prepareStatement("SELECT * FROM product");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Product product = new Product(rs.getInt("id"), rs.getString("name"), rs.getString("color"),
                    rs.getInt("price"));
            productList.add(product);
        }

        rs.close();
        ps.close();

        return productList;
    }

    public Human getHumanById(int humanId) throws SQLException {

        Human human = null;
        // @formatter:off
		String sqlQuery = """
				SELECT
				    h.id AS human_id,
				    h.name AS human_name,
				    h.capital,
				    p.id AS product_id,
				    p.name AS product_name,
				    p.color,
				    p.price
				FROM human h
				LEFT JOIN human_product_mapping hpm
				    ON h.id = hpm.human_id
				LEFT JOIN product p
				    ON p.id = hpm.product_id
				WHERE h.id = ?
				""";
		// @formatter:on
        PreparedStatement ps = conn.prepareStatement(sqlQuery);
        ps.setInt(1, humanId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            if (human == null) {
                // @formatter:off
				human = new Human(rs.getInt("human_id"),
						 		  rs.getString("human_name"), 
						 		  rs.getInt("capital"));
				// @formatter:on
            }

            if (rs.getObject("product_id") != null) {
                // @formatter:off
				Product product = new Product(rs.getInt("product_id"), 
											  rs.getString("product_name"),
											  rs.getString("color"), 
											  rs.getInt("price"));
				// @formatter:on
                human.addAllBoughtProducts(product);
            }
        }

        rs.close();
        ps.close();

        return human;
    }

    public void updateHuman(Human human) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("""
                		UPDATE human 
                		SET capital = ? 
                		WHERE id = ?
                """);
        ps.setInt(1, human.getCapital());
        ps.setInt(2, human.getId());
        ps.executeUpdate();

        ps.close();

        ps = conn.prepareStatement("""
                        	INSERT INTO human_product_mapping (human_id, product_id)
                        	VALUES (? , ?)
                """);

        for (Product iterProd : human.getNewBoughtProducts()) {
            ps.setInt(1, human.getId());
            ps.setInt(2, iterProd.getId());
            ps.executeUpdate();
        }

        ps.close();
    }
}
