package org.example.Controller;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.javalin.http.Header;
import org.example.Exception.ProductException;
import org.example.Exception.SellerException;
import org.example.Main;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class InventoryController {

    static SellerService sellerService;
    static ProductService productService;

    public InventoryController(SellerService sellerService, ProductService productService) {
        this.sellerService = sellerService;
        this.productService = productService;
    }

    public Javalin getAPI() {
        Javalin api = Javalin.create();

        api.before (ctx -> {
            ctx.header("Access-Control-Allow-Origin", "*");
            ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            ctx.header("Access-Control-Allow-Headers", "*");
        });

        //Javalin to handle preflight requests (sent via OPTIONS)
        api.options("/*", ctx -> {
            ctx.header(Header.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:3000");
            ctx.header(Header.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE, OPTIONS");
            ctx.header(Header.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Authorization");
            ctx.status(200);
        });


        /** Server Health Check */
        api.get("/health/",
                context ->
                {
                    context.result("the server is UP");
                }
        );

        /**Seller API calls*/
        api.get("/seller/", InventoryController::getAllSellerHandler);
        api.get ("/seller/{id}", InventoryController::getSellerByIdHandler);
        api.post("/seller/", InventoryController::postSellerHandler);

        /**Product API calls*/
        api.get("/product/", InventoryController::getAllProductHandler);
        api.post("/product/", InventoryController::postProductHandler);
        api.get("/product/{id}/", InventoryController::getProductByIdHandler);
        api.delete("/product/{id}", InventoryController::deleteProductByIdHandler);
        api.put("/product/{id}", InventoryController::updateProductByIdHandler);

        return api;
    }

    public static void getAllSellerHandler(Context context) {
        List<Seller> sellerList = sellerService.getAllSellers();
        context.json(sellerList);
    }

    public static void postSellerHandler(Context context) {
        //https://fasterxml.github.io/jackson-databind/javadoc/2.12/com/fasterxml/jackson/databind/ObjectMapper.html
        ObjectMapper om = new ObjectMapper();
        try {
            Seller seller1 = om.readValue(context.body(), Seller.class);
            sellerService.insertSeller(seller1);
//            201 - resource created
            context.status(201);
            context.json(seller1);
            context.result("Seller creation - SUCCESS");
            Main.log.warn("Seller creation - SUCCESS");

        } catch (JsonProcessingException e) {
//            Jackson was unable to parse the JSON, probably due to user error, so 400
            context.status(400);
            context.result(e.getMessage());
            e.printStackTrace();
            System.out.println("Error: Check JSON formatting");
        } catch (SellerException e){
            context.status(400);
            context.result(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void getSellerByIdHandler(Context context) throws SellerException {
        int id = Integer.parseInt(context.pathParam("id"));
        Seller seller = sellerService.getSellerById(id);
        if (seller == null) {
            context.status(404);
            context.result("No seller found");
        } else {
            context.json(seller);
            context.status(200);
        }
    }

    public static void getAllProductHandler(Context context) {
        List<Product> productList = productService.getAllProducts();
        context.json(productList);
    }

    public static void postProductHandler(Context context){
        try {
            ObjectMapper om = new ObjectMapper();
            Product p = om.readValue(context.body(), Product.class);
            Product newProduct = productService.insertProduct(p);
            context.status(201);
            context.json(newProduct);
            Main.log.warn("Product creation - SUCCESS");
        } catch (JsonProcessingException e) {
            context.status(400);
        } catch (ProductException e) {
            context.result(e.getMessage());
            context.status(400);
        } catch (NullPointerException e) {
            context.result(e.getMessage());
            context.status(400);
        }
    }

    public static void getProductByIdHandler(Context context) throws ProductException {
        int id = Integer.parseInt(context.pathParam("id"));
        Product product = productService.getProductById(id);
        if (product == null) {
            context.status(404);
            context.result("No products found");
        } else {
            context.json(product);
            context.status(200);
        }
    }

    public static void deleteProductByIdHandler(Context context) throws ProductException {
        int id = Integer.parseInt(context.pathParam("id"));
        Product product = productService.getProductById(id);
        if (product == null) {
            Main.log.warn("Product deletion - FAILED");
            context.result("Product deletion - FAILED");
            context.status(200);
        } else {
          //  productDAO.deleteProductById(id);

            productService.deleteProductById(id);
            Main.log.warn("Product deletion - SUCCESS");
            context.result("Product deletion - SUCCESS");
            context.status(200);
        }
    }

    public static void updateProductByIdHandler(Context context) throws ProductException {
        //retrieve ID
        int productId = Integer.parseInt(context.pathParam("id"));
        Product oldProduct = productService.getProductById(productId);

        //confirm that the product id is not null. If it is, then FAIL
        if (productId == 0) {
            Main.log.warn("Product update - FAILED");
            context.result("Product update - FAILED. Id is NULL");
            context.status(200);
        } else {

            try {
                //convert the ID into the index in the array
//                int index = ProductService.getPosition(productId);
//                int oldProductId = oldProduct.getProductId();

                //retrieve new product information
                ObjectMapper om = new ObjectMapper();
                Product newProduct = om.readValue(context.body(), Product.class);

                //update product
                productService.updateProductById(newProduct);

                Main.log.warn("Product update - SUCCESS");
                context.result("Product update - SUCCESS");
                context.status(200);
                context.json(newProduct);
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            } catch (JsonProcessingException  | NullPointerException | ProductException e) {
                context.result(e.getMessage());
                context.status(400);
            }

        }
    }
}

