package org.example.Model;

/**
 *     Seller
 *         Seller Name (must be unique)
 */
public class Seller {
    private String sellerName;

    public Seller() {
    }
    //check that the seller name is unique
    public Seller(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

}
