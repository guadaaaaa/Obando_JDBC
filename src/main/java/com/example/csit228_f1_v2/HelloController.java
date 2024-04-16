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
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    protected void onSigninClick() throws IOException {
        Parent homeview = FXMLLoader.load(HelloApplication.class
                .getResource("home-view.fxml"));
        AnchorPane p = (AnchorPane) pnLogin.getParent();
        p.getChildren().remove(pnLogin);
        p.getChildren().add(homeview);
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}