package org.example;

import io.javalin.Javalin;
import org.example.Controller.InventoryController;
import org.example.DAO.ProductDAO;
import org.example.DAO.SellerDAO;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.Util.ConnectionSingleton;
import java.sql.Connection;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        Connection conn = ConnectionSingleton.getConnection();

        SellerDAO sellerDAO = new SellerDAO(conn);
        ProductDAO productDAO = new ProductDAO(conn);

        SellerService sellerService = new SellerService(sellerDAO);
        ProductService productService = new ProductService(productDAO);

        InventoryController inventoryController = new InventoryController(sellerService, productService);

        Javalin api = inventoryController.getAPI();
        api.start(9005);
        }
    }