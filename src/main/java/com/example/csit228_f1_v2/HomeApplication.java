package com.example.csit228_f1_v2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeApplication extends Application {
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root = fxmlLoader.load();
        HomeController hc = new HomeController();
        hc.displayContents();
        Button btnPost = hc.getBtnPost();
        btnPost.setOnAction(event ->{
            System.out.println("Hello");
            try {
                hc.onPostClick();
                hc.displayContents();
                Parent p = FXMLLoader.load(getClass().getResource("homepage.fxml"));
                Scene s = new Scene(p);
                stage.setScene(s);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
