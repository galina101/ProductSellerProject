package org.example.Model;

/**
 *     Seller
 *         Seller Name (must be unique)
 */
public class Seller {
    private Integer sellerId;
    private String sellerName;

    public Seller() {
    }

    public Seller(Integer sellerId, String sellerName) {
        this.sellerId = sellerId;
        this.sellerName = sellerName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }
}
