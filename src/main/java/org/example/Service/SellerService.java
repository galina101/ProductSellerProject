package org.example.Service;

import org.example.Model.Product;
import org.example.Model.Seller;

import java.util.ArrayList;
import java.util.List;

public class SellerService {

    List<Seller> sellerList;
    public SellerService(){
        sellerList = new ArrayList<>();
    }

    public List<Seller> getAllSellers(){
        return sellerList;
    }

    public void insertSeller(Seller seller){
        sellerList.add(seller);
    }

    @Override
    public String toString() {
        return "SellerService{" +
                "sellerList=" + sellerList +
                '}';
    }
}

