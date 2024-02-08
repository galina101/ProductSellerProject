package org.example.Model;

/**
 *   Product
 *         Product Id (must be unique)
 *         Product Name
 *         Price
 *         Seller Name
 */
public class Product {

    private Integer productId;
    private String productName;
    private Double price;
    private String sellerName;

    public Product() {

    }
    public Product(Integer productId, String productName, Double price, String sellerName) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.sellerName = sellerName;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Double getPrice() {
        return price;
    }

    public String getSellerName() {
        return sellerName;
    }

    //check for the product ID to be unique
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }


    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", sellerName='" + sellerName + '\'' +
                '}';
    }
}



