package org.example.Service;

import org.example.DAO.ProductDAO;
import org.example.DAO.SellerDAO;
import org.example.Model.Product;
import org.example.Exception.ProductException;

import java.util.List;
import java.util.Random;

public class ProductService {
    ProductDAO productDAO;
    SellerDAO sellerDAO;

    public ProductService(ProductDAO productDAO){
        this.productDAO = productDAO;
    }

    public List<Product> getAllProducts(){
        return productDAO.getAllProducts();
    }

    public Product insertProduct(Product product) throws ProductException{

        if (verifyProduct(product)) {

            //generate product ID
            Random random = new Random();
            int id = random.nextInt(Integer.MAX_VALUE);

            product.setProductId(id);
            productDAO.insertProduct(product);
        }
        return product;
    }

    public static Product getProductById(int id) throws ProductException {
        Product product = ProductDAO.getProductById(id);

        if(product == null){
            throw new ProductException("No product with such id found");
        }else{
            return product;
        }
    }

    public void deleteProductById(int id) {
        ProductDAO.deleteProductById(id);
    }

        public void updateProductById (Product product) throws ProductException {
            if (verifyProduct(product)) {
                ProductDAO.updateProductById(product);
            }
        }
    public boolean verifyProduct (Product product) throws ProductException{

        //product ID and product name cannot be null
        if(product.getProductId() == null || product.getProductName() == null|| product.getProductName() == ""){
            throw new ProductException("Error: Id and product name fields must be non-null");
        }
        //price cannot be null
        if (product.getPrice() == null){
            throw new ProductException("Error: Product price cannot be null");
        }
        //price must be over zero
        if (product.getPrice()<=0) {
            throw new ProductException("Error: Product price must be more than zero");
        }
        //if seller exists in the Seller array
        if(sellerDAO.getSellerById(product.getSellerId()) == null){
            throw new ProductException("Error: Seller does not exist");
        }
        return true;
    }
}
