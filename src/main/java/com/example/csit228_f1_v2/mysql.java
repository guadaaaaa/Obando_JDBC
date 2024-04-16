package com.example.csit228_f1_v2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class mysql {
    public void InsertData(String name, String password){
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO tblusers (name,email) VALUES (?,?)"
            )){
            statement.setString(1,name);
            statement.setString(2,password);
            int rowsInserted = statement.executeUpdate();
            System.out.println("Rows inserted: "+rowsInserted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
