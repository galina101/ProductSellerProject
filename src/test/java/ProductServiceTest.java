
import org.example.Exception.ProductException;
import org.example.Exception.SellerException;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.example.Controller.InventoryController.postSellerHandler;

public class ProductServiceTest {
    SellerService sellerService;
    ProductService productService;
    static HashSet<Seller> sellerList;
    List<Product> productList;
    @Before
    public void setUp(){
        sellerService = new SellerService();
        productService = new ProductService(sellerService);
    }
    //Add a seller to the sellerList
    @Test
    public void addSellerTest() throws SellerException {
        Seller seller = new Seller();
        seller.setSellerName("YKK");
        sellerService.insertSeller(seller);
        Assert.assertFalse(sellerList.isEmpty());

    }

@Test
    //Add a product to an array
    public void addProductTest() throws ProductException, SellerException {
        Seller seller = new Seller();
        seller.setSellerName("YKK");
        sellerService.insertSeller(seller);

        Product product = new Product();
        product.setProductId(123455);
        product.setProductName("vase");
        product.setPrice(40.00);
        product.setSellerName("YKK");

        List<Product> productList;

        System.out.println(productList.toString());

        productService.insertProduct(product);
        System.out.println(productList.isEmpty());

        Assert.assertFalse(productList.isEmpty()); //Which product list is referenced here?

    }

}
