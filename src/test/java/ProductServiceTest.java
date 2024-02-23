
import org.example.Exception.ProductException;
import org.example.Exception.SellerException;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static org.example.Service.ProductService.productList;
import static org.example.Service.SellerService.sellerList;

public class ProductServiceTest {
    SellerService sellerService;
    ProductService productService;
    @Before
    public void setUp(){
       // ConnectionSingleton.resetTestDatabase();
        // results in org.h2.jdbc.JdbcSQLSyntaxErrorException: Cannot drop "SELLER" because "CONSTRAINT_18" depends on it; SQL statement:
        // drop table seller if exists [90107-214]

        sellerService = new SellerService();
        productService = new ProductService(sellerService);
//        Seller seller = new Seller();
//        seller.setSellerName("YKK");

    }


//    @BeforeEach
//    public void initialize() throws SellerException{
//         Seller seller = new Seller();
//         seller.setSellerName("YKK");
//         sellerService.insertSeller(seller);
//    }

    //Add a seller to the sellerList
    @Test
    public void addSellerTest() throws SellerException {
        Seller seller = new Seller();
        seller.setSellerName("YKK");

       // HashSet<Seller> sellerList = sellerService.getAllSellers();
        // Removed the reference to sellerList and productList and relied on the static value provided in service classes
        // Imported those values
        sellerService.insertSeller(seller);
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

        HashSet<Seller> sellerList = sellerService.getAllSellers();
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
        product.setSellerId(seller.getSellerId());

        productService.insertProduct(product);

       // List<Product> productList = productService.getAllProducts();
        //Product actual = productList.get(0);

        Assert.assertFalse(productList.isEmpty());

    }

@Test
//Retrieve product by ID
    public void getProductByIdTest() throws SellerException, ProductException {
        Seller seller = new Seller();
        seller.setSellerName("YKK");
        sellerService.insertSeller(seller);

        Product product = new Product();
        product.setProductId(123455);
        product.setProductName("vase");
        product.setPrice(40.00);
        product.setSellerId(seller.getSellerId());

        Product product1 = new Product();
        product1.setProductId(34545);
        product1.setProductName("zipper");
        product1.setPrice(2.00);
        product1.setSellerId(seller.getSellerId());

        productService.insertProduct(product);
        productService.insertProduct(product1);

        List<Product> productList = productService.getAllProducts();

        int id = productList.get(0).getProductId();
        Product actual = ProductService.getProductById(id);

        Assert.assertSame(product,actual);

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

        //Exception exception = new ProductException("Seller does not exist");

        //https://junit.org/junit4/javadoc/4.13/org/junit/Assert.html#assertThrows(java.lang.String,%20java.lang.Class,%20org.junit.function.ThrowingRunnable)
       // https://www.baeldung.com/junit-assert-exception
        Assert.assertThrows(Exception.class,
                () -> {
                    productService.insertProduct(product1);
                });
    }

@Test
    //product name is null
    public void addProductNameIsNull() throws SellerException, ProductException {
        Seller seller = new Seller();
        seller.setSellerName("YKK");
        sellerService.insertSeller(seller);

        Product product = new Product();
        product.setProductId(123455);
        product.setProductName(null);
        product.setPrice(40.00);
        product.setSellerId(seller.getSellerId());

        Exception exception = new ProductException("Id and product name fields must be non-null");

        //https://junit.org/junit4/javadoc/4.13/org/junit/Assert.html#assertThrows(java.lang.String,%20java.lang.Class,%20org.junit.function.ThrowingRunnable)
        // https://www.baeldung.com/junit-assert-exception
        Assert.assertThrows(ProductException.class, //changed from Exception.class
                () -> {
                    productService.insertProduct(product);
                });
    }


    //product name is an empty string
    @Test
    public void addProductNameIsEmptyString() throws SellerException, ProductException {
        Seller seller = new Seller();
        seller.setSellerName("YKK");
        sellerService.insertSeller(seller);

        Product product = new Product();
        product.setProductId(123455);
        product.setProductName("");
        product.setPrice(40.00);
        product.setSellerId(seller.getSellerId());

        Exception exception = new ProductException("Id and product name fields must be non-null");

        //https://junit.org/junit4/javadoc/4.13/org/junit/Assert.html#assertThrows(java.lang.String,%20java.lang.Class,%20org.junit.function.ThrowingRunnable)
        // https://www.baeldung.com/junit-assert-exception
        Assert.assertThrows(Exception.class,
                () -> {
                    productService.insertProduct(product);
                });
    }

    //product price is null
    @Test
    public void addProductPriceIsNull() throws SellerException, ProductException {
        Seller seller = new Seller();
        seller.setSellerName("YKK");
        sellerService.insertSeller(seller);

        Product product = new Product();
        product.setProductId(123455);
        product.setProductName("vase");
        product.setPrice(null);
        product.setSellerId(seller.getSellerId());

        Exception exception = new ProductException("Product price must be more than zero");

        //https://junit.org/junit4/javadoc/4.13/org/junit/Assert.html#assertThrows(java.lang.String,%20java.lang.Class,%20org.junit.function.ThrowingRunnable)
        // https://www.baeldung.com/junit-assert-exception
        Assert.assertThrows(Exception.class,
                () -> {
                    productService.insertProduct(product);
                });
    }

    //product price is negative
    @Test
    public void addProductPriceIsNegative() throws SellerException, ProductException {
        Seller seller = new Seller();
        seller.setSellerName("YKK");
        sellerService.insertSeller(seller);

        Product product = new Product();
        product.setProductId(123455);
        product.setProductName("vase");
        product.setPrice(-1.0);
        product.setSellerId(seller.getSellerId());

        Exception exception = new ProductException("Product price must be more than zero");

        //https://junit.org/junit4/javadoc/4.13/org/junit/Assert.html#assertThrows(java.lang.String,%20java.lang.Class,%20org.junit.function.ThrowingRunnable)
        // https://www.baeldung.com/junit-assert-exception
        Assert.assertThrows(Exception.class,
                () -> {
                    productService.insertProduct(product);
                });
    }

    //get position by ID
    @Test
    public void getPositionByIDTest () throws SellerException, ProductException {
        //create seller #1
        Seller seller = new Seller();
        seller.setSellerName("Home Depot");
        sellerService.insertSeller(seller);

        //create seller #2
        Seller seller1 = new Seller();
        seller1.setSellerName("Amazon");
        sellerService.insertSeller(seller1);

        //create product #1
        Product product = new Product();
        product.setProductId(123455);
        product.setProductName("vase");
        product.setPrice(40.00);
        product.setSellerId(seller.getSellerId());

        //create product #2
        Product product1 = new Product();
        product1.setProductId(34545);
        product1.setProductName("zipper");
        product1.setPrice(2.00);
        product1.setSellerId(seller1.getSellerId());

        productService.insertProduct(product);
        productService.insertProduct(product1);

        //List<Product> productList = productService.getAllProducts();
        //retrieve the product ID at index 0
        int id = productList.get(0).getProductId();

        //pass the id at index 0 and confirm that it matches
        Assert.assertEquals(0,(long)productService.getPosition(id));
    }

    //Test update product by ID
    @Test
    public void updateProductByIdTest() throws SellerException, ProductException {
        Seller seller = new Seller(5678, "YKK");
        sellerService.insertSeller(seller);

        Seller seller1 = new Seller(1234,"Home Depot");
        sellerService.insertSeller(seller1);

        Product product = new Product();
        product.setProductId(123455);
        product.setProductName("vase");
        product.setPrice(40.00);
        product.setSellerId(seller.getSellerId());

        Product product1 = new Product();
        product1.setProductId(34545);
        product1.setProductName("zipper");
        product1.setPrice(2.00);
        product1.setSellerId(seller1.getSellerId());

        productService.insertProduct(product);
        productService.insertProduct(product1);

        List<Product> productList = productService.getAllProducts();

        int id = productList.get(0).getProductId();

        //Replace product at index 0 to product at index 1
        int index = productService.getPosition(id);
        productService.updateProductById(index,product1);
        Product actual = productList.get(0);

        Assert.assertSame(product1,actual);

    }
    //Test delete product by ID
    @Test
    public void deleteProductByIdTest() throws SellerException, ProductException {
        Seller seller = new Seller(1234, "YKK");;
        sellerService.insertSeller(seller);

        Seller seller1 = new Seller(5678,"Home Depot");
        sellerService.insertSeller(seller1);

        //create 2 products
        Product product = new Product();
        product.setProductId(123455);
        product.setProductName("vase");
        product.setPrice(40.00);
        product.setSellerId(seller.getSellerId());

        Product product1 = new Product();
        product1.setProductId(34545);
        product1.setProductName("zipper");
        product1.setPrice(2.00);
        product1.setSellerId(seller1.getSellerId());

        productService.insertProduct(product);
        productService.insertProduct(product1);

        List<Product> productList = productService.getAllProducts();

        int index = productList.get(0).getProductId();

        //Delete product at index 0
        productService.deleteProductById(index);


        int actual = 1;
        int expected = productList.size();

        Assert.assertEquals(actual,expected);

    }


    //Check for duplicate sellers
    @Test
    public void checkDuplicateSellers () throws SellerException, ProductException {
        Seller seller = new Seller();
        seller.setSellerName("Home Depot");
        sellerService.insertSeller(seller);

        Seller seller1 = new Seller();
        seller1.setSellerName("Home Depot");

        Exception exception = new ProductException("Duplicate Seller");

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


        Exception exception = new ProductException("Error: Seller name cannot be null");

        //https://junit.org/junit4/javadoc/4.13/org/junit/Assert.html#assertThrows(java.lang.String,%20java.lang.Class,%20org.junit.function.ThrowingRunnable)
        // https://www.baeldung.com/junit-assert-exception
        Assert.assertThrows(Exception.class,
                () -> {
                    sellerService.insertSeller(seller1);
                });
    }


}
