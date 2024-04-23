package com.example.csit228_f1_v2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        HomeController hc1 = new HomeController();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        Text txtWelcome = new Text("Welcome to CIT");
        txtWelcome.setFont(Font.font("Chiller", FontWeight.EXTRA_BOLD, 69));
        txtWelcome.setFill(Color.RED);
        grid.setPadding(new Insets(20));
        txtWelcome.setTextAlignment(TextAlignment.CENTER);
        grid.add(txtWelcome, 0, 0, 3, 1);

        Label lbUsername = new Label("Username: ");
        lbUsername.setTextFill(Color.LIGHTSKYBLUE);
        lbUsername.setFont(Font.font(30));
        grid.add(lbUsername, 0, 1);

        TextField tfUsername = new TextField();
        grid.add(tfUsername, 1, 1);
        tfUsername.setFont(Font.font(30));
        tfUsername.setId("txtUsername");

        Label lbPassword = new Label("Password");
        lbPassword.setFont(Font.font(30));
        lbPassword.setTextFill(Color.CHARTREUSE);
        grid.add(lbPassword, 0, 2);

        PasswordField pfPassword = new PasswordField();
        pfPassword.setFont(Font.font(30));
        grid.add(pfPassword, 1, 2);
        pfPassword.setId("txtPassword");

        TextField tmpPassword = new TextField(pfPassword.getText());
        tmpPassword.setFont(Font.font(30));
        grid.add(tmpPassword, 1, 2);
        tmpPassword.setVisible(false);

        ToggleButton btnShow = new ToggleButton("( )");
        btnShow.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tmpPassword.setText(pfPassword.getText());
                tmpPassword.setVisible(true);
            }
        });

        EventHandler<MouseEvent> release = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tmpPassword.setVisible(false);
                pfPassword.setText(tmpPassword.getText());
            }
        };

        btnShow.setOnMouseReleased(release);
        btnShow.setOnMouseExited(release);
        grid.add(btnShow, 2,2);

        Button btnRegister = new Button("Register");
        btnRegister.setFont(Font.font(40));
        grid.add(btnRegister, 0, 3, 2, 1);

        Button btnLogIn = new Button("Log In");
        btnLogIn.setFont(Font.font(40));
        grid.add(btnLogIn, 0, 4, 2, 1);

        Text txtError = new Text();
        txtError.setFill(Color.RED);

        btnRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                HelloController hc = new HelloController();
                System.out.println("Hello");
                try {
                    String name = tfUsername.getText();
                    String pass = pfPassword.getText();
                    hc.InsertData(name,pass);
                    Parent p = FXMLLoader.load(getClass().getResource("homepage.fxml"));
                    Scene s = new Scene(p);
                    stage.setScene(s);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnLogIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                HelloController hc = new HelloController();
                try{
                    String name = tfUsername.getText();
                    String pass = pfPassword.getText();
                    boolean res = hc.ReadData(name,pass);
                    if(res){
                        Parent p = FXMLLoader.load(getClass().getResource("homepage.fxml"));
                        Scene s = new Scene(p);
                        stage.setScene(s);
                        stage.show();
                    } else {
                        txtError.setText("Log In Error! Register Instead");
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        Scene scene = new Scene(grid, 700, 500, Color.BLACK);
        stage.setScene(scene);
        scene.setFill(Color.CORNFLOWERBLUE);
        stage.show();
        txtWelcome.minWidth(grid.getWidth());
    }

    public static void main(String[] args) {
        launch();
    }
}