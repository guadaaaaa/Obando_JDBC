package com.example.csit228_f1_v2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class HelloController {
    public GridPane pnLogin;
    public AnchorPane pnMain;
    public VBox pnHome;
    public static int CurrentID;
    public static String CurrentName;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void CreateTableUsers(){
        Connection c = MySQLConnection.getConnection();
        String query = "CREATE TABLE tblusers (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(50) NOT NULL," +
                "password VARCHAR(100) NOT NULL)";
        try {
            Statement statement = c.createStatement();
            statement.execute(query);
            System.out.println("Table Created Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    @FXML
    public void InsertData(String name, String password){
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO tblusers (name,password) VALUES (?,?)"
            )){
            statement.setString(1,name);
            statement.setString(2,password);
            int rowsInserted = statement.executeUpdate();
            System.out.println("Rows inserted: "+rowsInserted);
            ReadData(name,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public boolean ReadData(String name, String password){
        try(Connection c = MySQLConnection.getConnection();
            Statement statement = c.createStatement()) {
            String query = "SELECT * FROM tblusers";
            ResultSet res = statement.executeQuery(query);
            while (res.next()){
                String currName = res.getString("name");
                String currPass = res.getString("password");
                int id = res.getInt("id");
                System.out.println("ID: " + id + "\nName:" + currName + "\nPassword: " + currPass);
                if(name.equals(currName) && password.equals(currPass)){
                    CurrentName = currName;
                    CurrentID = id;
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}