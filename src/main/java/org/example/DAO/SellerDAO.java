package org.example.DAO;

import org.example.Exception.SellerException;
import org.example.Model.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SellerDAO {
    static Connection conn;

    public SellerDAO(Connection conn){
        this.conn = conn;
    }


    public void insertSeller(Seller seller){
        try {
            PreparedStatement ps1 = conn.prepareStatement(
                    "insert into seller (seller_id, seller_name) " +
                            "values (?, ?)");
            ps1.setInt(1, seller.getSellerId());
            ps1.setString(2, seller.getSellerName());

            ps1.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static List<Seller> getAllSellers(){
        List<Seller> sellerResults = new ArrayList<>();
        try {
            PreparedStatement ps2 = conn.prepareStatement("select * from seller");
            ResultSet rs = ps2.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("seller_id");
                String name = rs.getString("seller_name");
                Seller seller = new Seller(id, name);
                sellerResults.add(seller);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return sellerResults;
    }
    public static Seller getSellerById(int id){
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

    public static boolean sellerNameExists(String name) throws SellerException {
        try{
            PreparedStatement ps = conn.prepareStatement(
                    "select * from seller where UPPER(seller_name) = UPPER(?)");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                int sellerId = rs.getInt("seller_id");
                String sellerName = rs.getString("seller_name");
                Seller seller = new Seller(sellerId, sellerName);

                String existingSellerName = seller.getSellerName();
                String newSellerName = seller.getSellerName();
                int comparison = newSellerName.compareToIgnoreCase(existingSellerName);
                if (comparison == 0) {
                    throw new SellerException("Error: Duplicate Seller");
                } else {
                    return false;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}