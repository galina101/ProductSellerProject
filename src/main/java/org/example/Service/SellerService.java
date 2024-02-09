package org.example.Service;

import org.example.Model.Product;
import org.example.Model.Seller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class SellerService {

    //HashSet will check that the seller name is unique
    static HashSet<Seller> sellerList;
    public SellerService(){
        sellerList = new HashSet<>();
    }

    public HashSet<Seller> getAllSellers(){
        return sellerList;
    }

    public static void insertSeller(Seller seller){
        sellerList.add(seller);
    }

    @Override
    public String toString() {
        return "SellerService{" +
                "sellerList=" + sellerList +
                '}';
    }

    public Seller getSellerByName(String sellerName){
        Iterator<Seller> i = sellerList.iterator();

        while (i.hasNext()){
            Seller currentSeller = i.next();
            if(currentSeller.getSellerName().equalsIgnoreCase(sellerName)){
                return currentSeller;
            }
       }
        return null;
    }
}


