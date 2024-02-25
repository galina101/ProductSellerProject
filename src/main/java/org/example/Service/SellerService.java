package org.example.Service;

import org.example.DAO.SellerDAO;
import org.example.Exception.SellerException;
import org.example.Model.Seller;
import java.util.List;
import java.util.Random;

public class SellerService {
    SellerDAO sellerDAO;

    public SellerService(SellerDAO sellerDAO) {
        this.sellerDAO = sellerDAO;
    }

    public List<Seller> getAllSellers() {
        return sellerDAO.getAllSellers();
    }

    public void insertSeller(Seller seller) throws SellerException {

        //check if seller name is null
        if (seller.getSellerName() == null || seller.getSellerName() == null || seller.getSellerName() == "") {
            throw new SellerException("Error: Seller name cannot be null");
        }

        //check if seller name already exists in seller list
        String newSellerName = seller.getSellerName();

       SellerDAO.sellerNameExists(newSellerName);

        //generate seller ID
        Random random = new Random();
        int id = random.nextInt(Integer.MAX_VALUE);

        seller.setSellerId(id);
        sellerDAO.insertSeller(seller);
    }
    public Seller getSellerById(Integer id) throws SellerException {
        Seller s = SellerDAO.getSellerById(id);

        if(s == null){
            throw new SellerException("No seller with such id found");
        }else{
            return s;
        }
    }
}