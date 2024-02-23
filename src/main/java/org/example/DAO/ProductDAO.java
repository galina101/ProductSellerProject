package org.example.DAO;

import org.example.Model.Product;
import org.example.Model.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {


    Connection conn;

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
            PreparedStatement ps2 = conn.prepareStatement("select * from seller");
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
    public Seller getSellerById(int id){
        try{
            PreparedStatement ps = conn.prepareStatement(
                    "select * from seller where seller_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int sellerId = rs.getInt("seller_id");
                String sellerName = rs.getString("seller_name");
                Seller seller = new Seller(sellerId, sellerName);
                return seller;
            }else{
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
