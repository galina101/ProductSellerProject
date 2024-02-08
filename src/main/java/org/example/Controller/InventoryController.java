package org.example.Controller;
import org.example.Exception.ProductException;
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
        this.sellerService = new SellerService();
        this.productService = new ProductService(sellerService);
    }

    public Javalin getAPI(){
        Javalin api = Javalin.create();

        api.get("/health/",
                context ->
                {
                    context.result("the server is UP");
                }
        );
        api.get("/seller/", InventoryController::getAllSellerHandler);
        api.post("/seller/", InventoryController::postSellerHandler);

        api.get("product", InventoryController::getAllProductHandler);
        api.post ("product", InventoryController::postProductHandler);

        api.get("/product/{id}/", InventoryController::getProductByIdHandler);

        api.delete("product/{id}", InventoryController::deleteProductByIdHandler);

        return api;
    }

    public static void getAllSellerHandler(Context context){
        List<Seller> sellerList = sellerService.getAllSellers();
        context.json(sellerList);
    }
    public static void postSellerHandler(Context context) throws JsonProcessingException {
        //https://fasterxml.github.io/jackson-databind/javadoc/2.12/com/fasterxml/jackson/databind/ObjectMapper.html
        ObjectMapper om = new ObjectMapper();
        try{
            Seller seller1 = om.readValue(context.body(), Seller.class);
            sellerService.insertSeller(seller1);
//            201 - resource created
            context.status(201);
            sellerService.toString();
        } catch (JsonProcessingException e) {
//            Jackson was unable to parse the JSON, probably due to user error, so 400
            context.status(400);
            System.out.println("Check JSON formatting");
        }
    }
    public static void getAllProductHandler(Context context){
        List<Product> productList = productService.getAllProducts();
        context.json(productList);
    }
    public static void postProductHandler(Context context) throws JsonProcessingException {
        try{
            ObjectMapper om = new ObjectMapper();
            Product p = om.readValue(context.body(), Product.class);
            Product newProduct = productService.insertProduct(p);
            context.status(201);
            context.json(newProduct);
        }catch(JsonProcessingException e){
            context.status(400);
        }catch(ProductException e){
            context.result(e.getMessage());
            context.status(400);
        }
    }

    public static void getProductByIdHandler(Context context){
        int id = Integer.parseInt(context.pathParam("id"));
        Product product = productService.getProductById(id);
        if(product == null){
            context.status(404);
            context.result("No products found");
        }else{
            context.json(product);
            context.status(200);
        }
    }

    public static void deleteProductByIdHandler(Context context){
        int id = Integer.parseInt(context.pathParam("id"));
        Product product = productService.getProductById(id);
        if(product == null){
            context.status(404);
        }else{
            //TODO:complete delete method
            //productService.deleteProductById(id);
            context.result("Product was deleted");
            context.status(200);
        }
    }



}
