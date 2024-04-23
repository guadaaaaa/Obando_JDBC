package com.example.csit228_f1_v2;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.sql.*;

public class HomeController {

    public ToggleButton tbNight;
    public ProgressIndicator piProgress;
    public Slider slSlider;
    public ProgressBar pbProgress;
    public TextArea txtPost;
    @FXML
    public Button btnPost;
    public AnchorPane parentPane;
    public TextArea txtContents;

    @FXML
    public void CreateTablePost(){
        Connection c = MySQLConnection.getConnection();
        String query = "CREATE TABLE tblpost (" +
                "postid INT PRIMARY KEY AUTO_INCREMENT," +
                "acctid INT(10) NOT NULL," +
                "postcontent VARCHAR(50) NOT NULL," +
                "FOREIGN KEY (acctid) REFERENCES tblusers(id))";
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

    public void onSliderChange() {
        double val = slSlider.getValue();
        System.out.println(val);
        piProgress.setProgress(val/100);
        pbProgress.setProgress(val/100);
        if (val == 100) {
            System.exit(0);
        }
    }

    public void onNightModeClick() {
        if (tbNight.isSelected()) {
            tbNight.getParent().setStyle("-fx-background-color: BLACK");
            tbNight.setText("DAY");
        } else {
            tbNight.getParent().setStyle("-fx-background-color: WHITE");
            tbNight.setText("NIGHT");
        }
    }


    public void onPostClick() {
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO tblpost (acctid,postcontent) VALUES (?,?)"
            )){
            HelloController hc = new HelloController();
            String content = txtPost.getText().toString();
            statement.setString(1, String.valueOf(hc.CurrentID));
            statement.setString(2, content);
            int rowsInserted = statement.executeUpdate();
            System.out.println("Rows inserted: "+rowsInserted);
            txtPost.setText("");
            displayContents();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Button getBtnPost() {
        return btnPost;
    }

    public void displayContents(){
        try(Connection c = MySQLConnection.getConnection();
        Statement statement = c.createStatement()){
            String query = "SELECT * FROM tblpost post INNER JOIN tblusers user ON post.acctid = user.id";
            ResultSet rs = statement.executeQuery(query);
            if(!rs.next()){
                System.out.println("No rows found");
            } else {
                while(rs.next()){
                    txtContents.appendText(rs.getString("name") + "\n" + rs.getString("postcontent") + "\n\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
