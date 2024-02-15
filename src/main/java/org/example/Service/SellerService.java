package org.example.Service;

import org.example.Exception.SellerException;
import org.example.Model.Seller;
import java.util.HashSet;
import java.util.Iterator;

public class SellerService {

    //HashSet will check that the seller name is unique
    static HashSet<Seller> sellerList;
    public SellerService(){
        sellerList = new HashSet<>();
    }

    public HashSet<Seller> getAllSellers(){
        return sellerList;
    }

    public static void insertSeller(Seller seller) throws SellerException {

        //check if seller name is null
        if (seller.getSellerName() == null || seller.getSellerName() == null || seller.getSellerName() == "") {
            throw new SellerException("Error: Seller name cannot be null");
        }

        //check if seller name already exists in seller list
        Iterator<Seller> it = sellerList.iterator();
        while (it.hasNext()) {
           String existingSellerName = it.next().getSellerName();
           String newSellerName = seller.getSellerName();
           int comparison = newSellerName.compareToIgnoreCase(existingSellerName);
           if (comparison == 0) {
               throw new SellerException("Error: Duplicate Seller");
            }

        }
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


