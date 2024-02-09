
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.example.Controller.InventoryController.postSellerHandler;

public class ProductServiceTest {
    ProductServiceTest productServiceTest;


    @Before
   // public void setUp () {productServiceTest = new ProductService();}

    //Add a seller to the array
    @Test
    public void addSellerTest() {
        Seller seller = new Seller();
       SellerService.insertSeller(seller);

    }


    //Add a product to an array


}
