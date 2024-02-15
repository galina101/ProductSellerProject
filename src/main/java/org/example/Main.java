package org.example;

import io.javalin.Javalin;
import org.example.Controller.InventoryController;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.Util.ConnectionSingleton;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {

        // this line is just for testing that your tables get set up correctly
        // if not, you'll get a stack trace
        ConnectionSingleton.getConnection();

        SellerService sellerService = new SellerService();
        ProductService productService = new ProductService(sellerService);
        InventoryController inventoryController = new InventoryController(sellerService, productService);

        Javalin api = inventoryController.getAPI();
        api.start(9005);
        }
    }