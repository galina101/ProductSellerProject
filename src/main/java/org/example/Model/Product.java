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
    private Integer sellerId;

    public Product() {

    }
    public Product(Integer productId, String productName, Double price, Integer sellerId) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.sellerId = sellerId;
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

    public Integer getSellerId() {return sellerId;}


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

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }


    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", sellerId='" + sellerId + '\'' +
                '}';
    }
}



