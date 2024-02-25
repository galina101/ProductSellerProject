
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

public class ProductServiceTest {
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
    }


//    @BeforeEach
//    public void initialize() throws SellerException{
//         Seller seller = new Seller();
//         seller.setSellerName("YKK");
//         sellerService.insertSeller(seller);
//    }


@Test
    //Add a product to the DB
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

        List<Product> productList = productDAO.getAllProducts();
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

        //What products are in the DB?
        List<Product> productList = productService.getAllProducts();

        //Get the ID of the first product in the DB
        int firstProductId = productList.get(0).getProductId();

        //Use that product ID to retrieve the rest of the Product object
        Product productInList = productService.getProductById(firstProductId);

        //Retrieve the product from DB and compare the Product fields
        Product productInDB = ProductDAO.getProductById(firstProductId);

        Assert.assertEquals(productInList,productInDB);

    }

    @Test
//Retrieve product by ID - no product found
    public void getProductByIdNullReturned() throws SellerException, ProductException {
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

        Assert.assertThrows(ProductException.class,
                () -> {
                    //Retrieve the product from DB with non-existing id
                    ProductService.getProductById(0);
                });
      }
@Test
    //product name is null
    public void addProductNameIsNull() throws SellerException {
        Seller seller = new Seller();
        seller.setSellerName("YKK");
        sellerService.insertSeller(seller);

        Product product = new Product();
        product.setProductId(123455);
        product.setProductName(null);
        product.setPrice(40.00);
        product.setSellerId(seller.getSellerId());

        //https://junit.org/junit4/javadoc/4.13/org/junit/Assert.html#assertThrows(java.lang.String,%20java.lang.Class,%20org.junit.function.ThrowingRunnable)
        // https://www.baeldung.com/junit-assert-exception
        Assert.assertThrows(ProductException.class, //changed from Exception.class
                () -> {
                    productService.insertProduct(product);
                });
    }


    //product name is an empty string
    @Test
    public void addProductNameIsEmptyString() throws SellerException {
        Seller seller = new Seller();
        seller.setSellerName("YKK");
        sellerService.insertSeller(seller);

        Product product = new Product();
        product.setProductId(123455);
        product.setProductName("");
        product.setPrice(40.00);
        product.setSellerId(seller.getSellerId());

        //https://junit.org/junit4/javadoc/4.13/org/junit/Assert.html#assertThrows(java.lang.String,%20java.lang.Class,%20org.junit.function.ThrowingRunnable)
        // https://www.baeldung.com/junit-assert-exception
        Assert.assertThrows(ProductException.class,
                () -> {
                    productService.insertProduct(product);
                });
    }

    //product price is null
    @Test
    public void addProductPriceIsNull() throws SellerException {
        Seller seller = new Seller();
        seller.setSellerName("YKK");
        sellerService.insertSeller(seller);

        Product product = new Product();
        product.setProductId(123455);
        product.setProductName("vase");
        product.setPrice(null);
        product.setSellerId(seller.getSellerId());

        //https://junit.org/junit4/javadoc/4.13/org/junit/Assert.html#assertThrows(java.lang.String,%20java.lang.Class,%20org.junit.function.ThrowingRunnable)
        // https://www.baeldung.com/junit-assert-exception
        Assert.assertThrows(ProductException.class,
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

        //https://junit.org/junit4/javadoc/4.13/org/junit/Assert.html#assertThrows(java.lang.String,%20java.lang.Class,%20org.junit.function.ThrowingRunnable)
        // https://www.baeldung.com/junit-assert-exception
        Assert.assertThrows(ProductException.class,
                () -> {
                    productService.insertProduct(product);
                });
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

        //save the products as they were saved in DB with their product IDs
        Product productInDB = productService.insertProduct(product);
        Product product1InDB = productService.insertProduct(product1);

        Product newProduct = new Product();
        newProduct.setProductId(productInDB.getProductId());
        newProduct.setProductName("bolt");
        newProduct.setPrice(3.00);
        newProduct.setSellerId(seller.getSellerId());

        //pass new product updated details to update
        productService.updateProductById(newProduct);


        //Retrieve the product from DB and compare the Product fields
        Product updatedProductInDB = ProductDAO.getProductById(productInDB.getProductId());

        Assert.assertEquals(newProduct,updatedProductInDB);
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

        Product productInDB = productService.insertProduct(product);
        Product productInDB1 = productService.insertProduct(product1);

        int deletedProductID = productInDB.getProductId();

        productService.deleteProductById(deletedProductID);

        Assert.assertThrows(ProductException.class,
                () -> {
                    productService.getProductById(deletedProductID);
                });

    }
}