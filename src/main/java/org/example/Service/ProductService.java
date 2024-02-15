package org.example.Service;

import org.example.Model.Product;
import org.example.Exception.ProductException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProductService {
    SellerService sellerService;
    static List<Product> productList;
    public ProductService(SellerService sellerService){
        this.sellerService = sellerService;
        productList = new ArrayList<>();
    }

    public List<Product> getAllProducts(){
        return productList;
    }

    public Product insertProduct(Product product) throws ProductException{

        if (verifyProduct(product)) {

            //generate product ID
            Random random = new Random();
            int id = random.nextInt(Integer.MAX_VALUE);
            //int id = (int) (Math.random() * Integer.MAX_VALUE);

            product.setProductId(id);
            productList.add(product);
        }
        return product;
    }

    public static Product getProductById(int id){
        Product currentProduct;

        for(int i = 0; i < productList.size(); i++){
            currentProduct = productList.get(i);
            if(currentProduct.getProductId() == id){

                return currentProduct;
            }
        }
        return null;
    }
    public static Integer getPosition(int id){
        Product currentProduct;
        int i =0;

        for(i = 0; i < productList.size(); i++){
            currentProduct = productList.get(i);
            if(currentProduct.getProductId() == id){
                return i;
            }
        }
        return null;
        //throw ProductException("Product ID not found");
    }

    public void deleteProductById(int id){
        productList.remove(getProductById(id));
    }

    public void updateProductById (int id, Product product) throws ProductException {
        if (verifyProduct(product))
        {
            productList.set(id,product);
        }

    }

    public boolean verifyProduct (Product product) throws ProductException{

        //product ID and product name cannot be null
        if(product.getProductId() == null || product.getProductName() == null|| product.getProductName() == ""){
            throw new ProductException("Error: Id and product name fields must be non-null");
        }
        //price must be over zero
        if (product.getPrice()<=0 || product.getPrice() == null) {
            throw new ProductException("Error: Product price must be more than zero");
        }
        //if seller exists in the Seller array
        if(sellerService.getSellerByName(product.getSellerName()) == null){
            throw new ProductException("Error: Seller does not exist");
        }
      return true;
    }
}
