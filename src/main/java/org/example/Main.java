package org.example;

import io.javalin.Javalin;
import org.example.Controller.InventoryController;
import org.example.DAO.ProductDAO;
import org.example.DAO.SellerDAO;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.Util.ConnectionSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        Connection conn = ConnectionSingleton.getConnection();

        SellerDAO sellerDAO = new SellerDAO(conn);
        ProductDAO productDAO = new ProductDAO(conn);

        SellerService sellerService = new SellerService();
        ProductService productService = new ProductService(sellerService);

        InventoryController inventoryController = new InventoryController(sellerService, productService);

        Javalin api = inventoryController.getAPI();
        api.start(9005);
        }
    }