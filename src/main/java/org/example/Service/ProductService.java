package org.example.Service;

import org.example.Model.Product;
import org.example.Exception.ProductException;
import org.example.Model.Seller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProductService {
    SellerService sellerService;
    List<Product> productList;
    public ProductService(SellerService sellerService){
        this.sellerService = sellerService;
        productList = new ArrayList<>();
    }

    public List<Product> getAllProducts(){
        return productList;
    }

    public Product insertProduct(Product product) throws ProductException{
        if(product.getProductId() == null || product.getProductName() == null){
            throw new ProductException("id and product name fields must be non-null");
        }
        Random random = new Random();
        int id = random.nextInt (Integer.MAX_VALUE);
        //int id = (int) (Math.random() * Integer.MAX_VALUE);
        product.setProductId(id);
        productList.add(product);
        return product;

    }

    public Product getProductById(int id){
        for(int i = 0; i < productList.size(); i++){
            Product currentProduct = productList.get(i);
            if(currentProduct.getProductId() == id){
                return currentProduct;
            }
        }
        return null;
    }
}
