
import org.example.DAO.ProductDAO;
import org.example.DAO.SellerDAO;
import org.example.Exception.ProductException;
import org.example.Exception.SellerException;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.example.Util.ConnectionSingleton;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;
import java.util.Random;

public class SellerServiceTest {

    SellerService sellerService;
    ProductService productService;
    SellerDAO sellerDAO;
    ProductDAO productDAO;

    Connection conn = ConnectionSingleton.getConnection();
    @Before
    public void setUp(){
        ConnectionSingleton.resetTestDatabase();

        sellerDAO = new SellerDAO(conn);
        productDAO = new ProductDAO(conn);

        sellerService = new SellerService(sellerDAO);
        productService = new ProductService(productDAO);
//        Seller seller = new Seller();
//        seller.setSellerName("YKK");

    }

    //Add a seller to the DB
    @Test
    public void addSellerTest() throws SellerException {
        Seller seller = new Seller();
        seller.setSellerName("YKK");

        sellerService.insertSeller(seller);

        List <Seller> sellerList = sellerService.getAllSellers();

        //Check that DB returns the row with 1 seller
        Assert.assertFalse(sellerList.isEmpty());

    }

    //Add multiple sellers
    @Test
    public void addMultipleSellerTest() throws SellerException {
        Seller seller = new Seller();
        seller.setSellerName("YKK");
        sellerService.insertSeller(seller);

        Seller seller1 = new Seller();
        seller1.setSellerName("Home Depot");
        sellerService.insertSeller(seller1);

        List<Seller> sellerList = sellerService.getAllSellers();
        Assert.assertFalse(sellerList.isEmpty());

    }
    @Test
    //Seller doesn't exist
    public void addProductInvalidSeller() throws SellerException, ProductException {
        Seller seller = new Seller();
        seller.setSellerName("YKK");
        sellerService.insertSeller(seller);

        //create product #1
        Product product = new Product();
        product.setProductId(123455);
        product.setProductName("vase");
        product.setPrice(40.00);
        product.setSellerId(seller.getSellerId());

        //Generate a seller ID that does NOT match the ID of the valid seller
        Random random = new Random();
        int id = random.nextInt(seller.getSellerId());

        //create product #2
        Product product1 = new Product();
        product1.setProductId(34545);
        product1.setProductName("zipper");
        product1.setPrice(2.00);
        product1.setSellerId(id);

        productService.insertProduct(product);

        //https://junit.org/junit4/javadoc/4.13/org/junit/Assert.html#assertThrows(java.lang.String,%20java.lang.Class,%20org.junit.function.ThrowingRunnable)
        // https://www.baeldung.com/junit-assert-exception
        Assert.assertThrows(Exception.class,
                () -> {
                    productService.insertProduct(product1);
                });
    }

    @Test
    //Retrieve seller by ID
    public void getSellerByIdTest() throws SellerException, ProductException {
        Seller seller = new Seller();
        seller.setSellerName("YKK");
        sellerService.insertSeller(seller);

        //   create seller #2
        Seller seller1 = new Seller();
        seller1.setSellerName("Amazon");
        sellerService.insertSeller(seller1);

//        sellerService.insertSeller(seller);
//        sellerService.insertSeller(seller1);

        //What products are in the DB?
        List<Seller> sellerList = sellerService.getAllSellers();

        //Get the ID of the first product in the DB
        int firstSellerId = sellerList.get(0).getSellerId();

        //Use that product ID to retrieve the rest of the Product object
        Seller sellerInList = sellerService.getSellerById(firstSellerId);

        //Retrieve the product from DB and compare the Product fields
        Seller sellerInDB = SellerDAO.getSellerById(firstSellerId);

        Assert.assertEquals(sellerInList,sellerInDB);

    }
    //Check for duplicate sellers
    @Test
    public void checkDuplicateSellers () throws SellerException {
        Seller seller = new Seller();
        seller.setSellerName("Home Depot");
        sellerService.insertSeller(seller);

        Seller seller1 = new Seller();
        seller1.setSellerName("Home Depot");

        //https://junit.org/junit4/javadoc/4.13/org/junit/Assert.html#assertThrows(java.lang.String,%20java.lang.Class,%20org.junit.function.ThrowingRunnable)
        // https://www.baeldung.com/junit-assert-exception
        Assert.assertThrows(Exception.class,
                () -> {
                    sellerService.insertSeller(seller1);
                });


    }


    @Test
    //seller name is null
    public void addSellerNameIsNull() throws SellerException, ProductException {
        Seller seller = new Seller();
        seller.setSellerName("YKK");
        sellerService.insertSeller(seller);

        Seller seller1 = new Seller();
        seller1.setSellerName(null);

        //https://junit.org/junit4/javadoc/4.13/org/junit/Assert.html#assertThrows(java.lang.String,%20java.lang.Class,%20org.junit.function.ThrowingRunnable)
        // https://www.baeldung.com/junit-assert-exception
        Assert.assertThrows(Exception.class,
                () -> {
                    sellerService.insertSeller(seller1);
                });
    }

    @Test
//Seller By ID - No Such seller_id exists
    public void getSellerByIdNoTfound() throws SellerException {
        Seller seller = new Seller();
        seller.setSellerName("YKK");
        sellerService.insertSeller(seller);

        //   create seller #2
        Seller seller1 = new Seller();
        seller1.setSellerName("Amazon");
        sellerService.insertSeller(seller1);

        Assert.assertThrows(Exception.class,
                () -> {
                    sellerService.getSellerById(0);
                });

    }
}
