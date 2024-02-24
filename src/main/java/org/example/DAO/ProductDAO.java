package org.example.DAO;

import org.example.Model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    static Connection conn;

    public ProductDAO(Connection conn){
        this.conn = conn;
    }


    public void insertProduct(Product product){
        try {
            PreparedStatement ps1 = conn.prepareStatement(
                    "insert into product (product_id, product_name, product_price, seller_id) " +
                            "values (?, ?, ?, ?)");
            ps1.setInt(1, product.getProductId());
            ps1.setString(2, product.getProductName());
            ps1.setDouble(3, product.getPrice());
            ps1.setInt(4, product.getSellerId());

            ps1.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts(){
        List<Product> sellerResults = new ArrayList<>();
        try {
            PreparedStatement ps2 = conn.prepareStatement("select * from product");
            ResultSet rs = ps2.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("product_id");
                String name = rs.getString("product_name");
                Double price = rs.getDouble("product_price");
                Integer sellerId = rs.getInt("seller_id");
                Product product = new Product(id, name, price, sellerId);
                sellerResults.add(product);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return sellerResults;
    }
    public static Product getProductById(int id){
        try{
            PreparedStatement ps = conn.prepareStatement(
                    "select * from product where product_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int productId = rs.getInt("product_id");
                String name = rs.getString("product_name");
                Double price = rs.getDouble("product_price");
                Integer sellerId = rs.getInt("seller_id");
                Product product = new Product(productId, name, price, sellerId);
                return product;
            }else{
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public static void deleteProductById(int id){
        try{
            PreparedStatement ps = conn.prepareStatement(
                    "delete from product where product_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

           }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void updateProductById(Product product){
        try{
            PreparedStatement ps = conn.prepareStatement(
                    "update product " +
                            "set " +
                            //" product_id = ?" +
                            " product_name = ? " +
                            ", product_price = ? " +
                            ", seller_id = ? "+
                            "where product_id = ?");
            //ps.setInt(1, product.getProductId());
            ps.setString(1, product.getProductName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getSellerId());
            ps.setInt(4, product.getProductId());

           ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
