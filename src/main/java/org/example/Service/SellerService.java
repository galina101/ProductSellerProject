package org.example.Service;

import org.example.Exception.SellerException;
import org.example.Main;
import org.example.Model.Seller;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import org.example.Model.Seller;

public class SellerService {

    //HashSet will check that the seller name is unique
    public static HashSet<Seller> sellerList;
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
        //generate seller ID
        Random random = new Random();
        int id = random.nextInt(Integer.MAX_VALUE);

        seller.setSellerId(id);
        sellerList.add(seller);
    }

    @Override
    public String toString() {
        return "SellerService{" +
                "sellerList=" + sellerList +
                '}';
    }

    public Seller getSellerById(Integer id){
        Iterator<Seller> i = sellerList.iterator();

        while (i.hasNext()){
            Seller currentSeller = i.next();
            Main.log.warn("currentSeller.getSellerId() " + currentSeller.getSellerId());
            Main.log.warn("id passed " + id);
            Main.log.warn("currentSeller.getSellerId() == id " + (currentSeller.getSellerId()).equals(id));

            if((currentSeller.getSellerId()).equals(id)){
                return currentSeller;
            }
       }
        return null;
    }
}


